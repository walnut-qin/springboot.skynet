package com.kaos.skynet.api.logic.service.inpatient.escort.core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.his.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.his.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.his.entity.inpatient.FinIprPrepayIn;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec.StateEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.annex.EscortAnnexInfoMapper;
import com.kaos.skynet.api.data.his.mapper.outpatient.fee.FinOpbFeeDetailMapper;
import com.kaos.skynet.api.data.his.tunnel.NatsTunnel;
import com.kaos.skynet.core.util.IntegerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * 陪护证状态服务
 */
@Log4j
@Validated
@Service
public class StateService {
    /**
     * 陪护证主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * 陪护证状态接口
     */
    @Autowired
    EscortStateRecMapper escortStateRecMapper;

    /**
     * 附件接口
     */
    @Autowired
    EscortAnnexInfoMapper escortAnnexInfoMapper;

    /**
     * 住院实体接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 住院证接口
     */
    @Autowired
    FinIprPrepayInMapper prepayInMapper;

    /**
     * 划价记录
     */
    @Autowired
    FinOpbFeeDetailMapper feeDetailMapper;

    /**
     * 核酸结果转换器
     */
    @Autowired
    NatsTunnel natsTunnel;

    /**
     * 患者基本信息缓存
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 查询陪护证状态
     * 
     * @param escortNo 陪护证编号
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result queryEscortState(@NotBlank(message = "陪护证号不能为空") String escortNo) {
        // 检索陪护证主表
        EscortMainInfo escortInfo = escortMainInfoMapper.queryEscortMainInfo(escortNo);
        if (escortInfo == null) {
            String errInfo = String.format("陪护证(%s)不存在", escortNo);
            log.error(errInfo);
            throw new RuntimeException(errInfo);
        }

        // 适配
        var result = queryEscortState(escortInfo);
        if (result.getState() == EscortStateRec.StateEnum.其他) {
            result.setState(EscortStateRec.StateEnum.无核酸检测结果);
        }

        return result;
    }

    /**
     * 根据陪护主表记录查询陪护状态
     * 
     * @param escortInfo
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result queryEscortState(@NotNull(message = "陪护证不能为空") EscortMainInfo escortInfo) {
        // 检索住院实体
        List<FinIprInMainInfo> inMainInfos = inMainInfoMapper.queryInMainInfos(FinIprInMainInfoMapper.Key.builder()
                .cardNo(escortInfo.getPatientCardNo())
                .happenNo(escortInfo.getHappenNo())
                .build());
        if (!inMainInfos.isEmpty()) {
            /**
             * 强陪护逻辑
             */
            // 筛选在院记录
            Long inCnt = inMainInfos.stream().filter(x -> {
                switch (x.getInState()) {
                    case 住院登记:
                    case 病房接诊:
                        return true;

                    default:
                        return false;
                }
            }).count();
            // 全部出院？
            if (inCnt == 0) {
                /**
                 * 逻辑增强，对于仅有一条的出院记录，作详细判断
                 */
                // 筛选出院记录
                var outMainInfos = inMainInfos.stream().filter(x -> {
                    switch (x.getInState()) {
                        case 住院登记:
                        case 病房接诊:
                            return false;

                        default:
                            return true;
                    }
                }).toList();
                switch (outMainInfos.size()) {
                    case 1 -> {
                        switch (outMainInfos.get(0).getInState()) {
                            case 出院登记 -> {
                                var duration = Duration.between(outMainInfos.get(0).getOutDate(), LocalDateTime.now());
                                if (duration.compareTo(Duration.ofHours(12)) < 0) {
                                    return core(escortInfo);
                                } else {
                                    return new Result(StateEnum.注销, "患者已出科达到12小时");
                                }
                            }

                            case 出院结算 -> {
                                var duration = Duration.between(outMainInfos.get(0).getOutDate(), LocalDateTime.now());
                                if (duration.compareTo(Duration.ofHours(6)) < 0) {
                                    return core(escortInfo);
                                } else {
                                    return new Result(StateEnum.注销, "患者已出院达到6小时");
                                }
                            }

                            default -> {
                                return new Result(StateEnum.注销, "患者已出院");
                            }
                        }
                    }

                    default -> {
                        return new Result(StateEnum.注销, "患者存在多条出院记录");
                    }
                }
            } else {
                return core(escortInfo);
            }
        } else {
            /**
             * 弱陪护逻辑
             */
            // 检索关联住院证
            var prepayIn = prepayInMapper.queryPrepayIn(escortInfo.getPatientCardNo(), escortInfo.getHappenNo());
            if (prepayIn == null) {
                return new Result(StateEnum.注销, "关联的住院证已被删除");
            } else {
                // 检索患者的所有住院证
                var allPrepayIns = prepayInMapper.queryPrepayIns(FinIprPrepayInMapper.Key.builder()
                        .cardNo(escortInfo.getPatientCardNo())
                        .build());
                // 逆序
                allPrepayIns.sort((x, y) -> {
                    return IntegerUtils.compare(y.getHappenNo(), x.getHappenNo());
                });
                // 最新判断
                if (!IntegerUtils.equals(prepayIn.getHappenNo(), allPrepayIns.get(0).getHappenNo())) {
                    return new Result(StateEnum.注销, "患者未入院, 且已开立新住院证");
                }
                // 住院证已作废？
                if (prepayIn.getState() == FinIprPrepayIn.InStateEnum.作废) {
                    return new Result(StateEnum.注销, "患者未入院, 且已开立新住院证");
                }
                // 执行核心逻辑
                return core(escortInfo);
            }
        }
    }

    /**
     * 核心逻辑
     * 
     * @param escortInfo
     * @return
     */
    private Result core(@NotNull EscortMainInfo escortInfo) {
        // 检索历史状态
        var historyStates = escortStateRecMapper.queryEscortStateRecs(escortInfo.getEscortNo());

        // 检索核酸结果
        NatsTunnel.Value natsResult = null;
        if (historyStates.isEmpty()) {
            // 检索14天内核酸结果
            natsResult = natsTunnel.tunneling(NatsTunnel.Key.builder()
                    .cardNos(Lists.newArrayList(escortInfo.getHelperCardNo()))
                    .duration(Duration.ofDays(14))
                    .build());
        } else {
            // 逆序
            historyStates.sort((x, y) -> {
                return IntegerUtils.compare(y.getRecNo(), x.getRecNo());
            });
            // 状态判断
            switch (historyStates.get(0).getState()) {
                case 注销 -> {
                    return new Result(StateEnum.注销, "陪护证已注销");
                }

                case 生效中 -> {
                    // 检索2天内核酸结果
                    natsResult = natsTunnel.tunneling(NatsTunnel.Key.builder()
                            .cardNos(Lists.newArrayList(escortInfo.getHelperCardNo()))
                            .duration(Duration.ofDays(14))
                            .build());
                }

                default -> {
                    // 检索14天内核酸结果
                    natsResult = natsTunnel.tunneling(NatsTunnel.Key.builder()
                            .cardNos(Lists.newArrayList(escortInfo.getHelperCardNo()))
                            .duration(Duration.ofDays(5))
                            .build());
                }
            }
        }

        // 核酸结果是否存在
        if (natsResult != null) {
            if (natsResult.getNegative()) {
                return new Result(StateEnum.生效中, natsResult.toString());
            } else {
                return new Result(StateEnum.其他, natsResult.toString());
            }
        }

        // 检索7日内核酸划价记录
        var feeDetails = feeDetailMapper.queryFeeDetails(FinOpbFeeDetailMapper.Key.builder()
                .cardNo(escortInfo.getHelperCardNo())
                .itemCode("F00000068231")
                .beginOperDate(LocalDateTime.now().minus(Duration.ofDays(7)))
                .build());
        if (!feeDetails.isEmpty()) {
            return new Result(StateEnum.等待院内核酸检测结果, "存在7天内的核酸划价记录");
        }

        // 14天内院外附件
        var annexInfos = escortAnnexInfoMapper.queryAnnexInfos(EscortAnnexInfoMapper.Key.builder()
                .cardNo(escortInfo.getHelperCardNo())
                .checked(false)
                .beginUploadDate(LocalDateTime.now().minusDays(14))
                .build());
        if (!annexInfos.isEmpty()) {
            return new Result(StateEnum.等待院外核酸检测结果审核, "存在未审核的院外报告");
        }

        // 检索陪护人信息
        var helper = patientInfoCache.get(escortInfo.getHelperCardNo());
        if (helper != null) {
            // 健康码判断
            if (helper.getHealthCode() != null) {
                switch (helper.getHealthCode()) {
                    case 绿码 -> {
                        // 通过
                    }

                    default -> {
                        return new Result(StateEnum.其他, "健康码异常");
                    }
                }
            }

            // 健康码判断
            if (helper.getTravelCode() != null) {
                switch (helper.getTravelCode()) {
                    case 正常 -> {
                        // 通过
                    }

                    default -> {
                        return new Result(StateEnum.其他, "行程码异常");
                    }
                }
            }

            // 健康码判断
            if (helper.getHighRiskFlag() != null) {
                if (!helper.getHighRiskFlag()) {
                    return new Result(StateEnum.其他, "经过高风险地区");
                }
            }
        }

        return new Result(StateEnum.无核酸检测结果, "无有效依据");
    }

    /**
     * 状态结果集
     */
    @Getter
    @AllArgsConstructor
    public static class Result {
        /**
         * 状态
         */
        @Setter
        private StateEnum state;

        /**
         * 判断原因
         */
        private String reason;
    }
}