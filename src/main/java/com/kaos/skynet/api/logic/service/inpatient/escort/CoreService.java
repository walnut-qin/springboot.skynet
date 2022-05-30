package com.kaos.skynet.api.logic.service.inpatient.escort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.kaos.skynet.api.data.cache.DataCache;
import com.kaos.skynet.api.data.cache.inpatient.FinIprInMainInfoCache;
import com.kaos.skynet.api.data.cache.inpatient.escort.annex.EscortAnnexCheckCache;
import com.kaos.skynet.api.data.cache.inpatient.escort.annex.EscortAnnexInfoCache;
import com.kaos.skynet.api.data.cache.outpatient.fee.FinOpbFeeDetailCache;
import com.kaos.skynet.api.data.cache.pipe.lis.LisResultNewCache;
import com.kaos.skynet.api.data.entity.common.ComPatientInfo;
import com.kaos.skynet.api.data.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexCheck;
import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexInfo;
import com.kaos.skynet.api.data.entity.outpatient.fee.FinOpbFeeDetail;
import com.kaos.skynet.api.data.entity.pipe.lis.LisResultNew;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.enums.inpatient.InStateEnum;
import com.kaos.skynet.api.enums.inpatient.escort.EscortStateEnum;
import com.kaos.skynet.core.Gsons;
import com.kaos.skynet.core.type.converter.duration.string.AgeDurationToStringConverter;
import com.kaos.skynet.core.type.utils.DurationUtils;
import com.kaos.skynet.core.type.utils.IntegerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
class CoreService {
    /**
     * 缓存
     */
    @Autowired
    DataCache cache;

    /**
     * 陪护主接口
     */
    @Autowired
    EscortMainInfoMapper mainInfoMapper;

    /**
     * 住院证接口
     */
    @Autowired
    FinIprPrepayInMapper prepayInMapper;

    /**
     * 序列化工具
     */
    final Gson gson = Gsons.newGson();

    /**
     * Duration转为字符串描述
     */
    final Converter<Duration, String> durationToStringConverter = new AgeDurationToStringConverter();

    /**
     * 逻辑核心 - 判断陪护证状态
     * 
     * @param escortMainInfo 陪护主表信息
     * @return
     */
    private StateResult getStateCore(EscortMainInfo escortMainInfo) {
        // 获取陪护人的信息实体
        ComPatientInfo helper = cache.getPatientInfoCache().get(escortMainInfo.getHelperCardNo());
        if (helper == null) {
            return StateResult.builder()
                    .state(EscortStateEnum.注销)
                    .reason(String.format("陪护人不存在(cardNo = %s)", escortMainInfo.getHelperCardNo()))
                    .build();
        }

        // 判断陪护人的健康码
        if (helper.getHealthCode() != null) {
            switch (helper.getHealthCode()) {
                case 绿码 -> {
                    // 通过此项判断, 不做任何事
                }
                default -> {
                    return StateResult.builder()
                            .state(EscortStateEnum.其他)
                            .reason(String.format("陪护人健康码异常(%s)", helper.getHealthCode().getDescription()))
                            .build();
                }
            }
        }

        // 判断行程码
        if (helper.getTravelCode() != null) {
            switch (helper.getTravelCode()) {
                case 正常 -> {
                    // 通过此项判断, 不做任何事
                }
                default -> {
                    return StateResult.builder()
                            .state(EscortStateEnum.其他)
                            .reason(String.format("陪护人行程码异常(%s)", helper.getTravelCode().getDescription()))
                            .build();
                }
            }
        }

        // 高风险地区标识判断
        if (helper.getHighRiskFlag() != null) {
            if (helper.getHighRiskFlag()) {
                return StateResult.builder()
                        .state(EscortStateEnum.其他)
                        .reason(String.format("陪护人经过了高风险地区(%s)", helper.getHighRiskArea()))
                        .build();
            }
        }

        // 决定有效核酸结果有效期
        Integer ptr = 0;
        List<EscortStateRec> stateRecs = cache.getEscortStateRecCache().get(escortMainInfo.getEscortNo());
        if (stateRecs != null && !stateRecs.isEmpty() && stateRecs.get(0).getState() == EscortStateEnum.生效中) {
            ptr = 14;
        } else {
            ptr = 2;
        }
        final Integer offset = ptr;

        // 本院核酸结果
        List<LisResultNew> lisResults = cache.getLisResultCache().get(LisResultNewCache.Key.builder()
                .patientId(helper.getCardNo())
                .itemCodes(Lists.newArrayList("SARS-CoV-2-RNA"))
                .offset(offset).build());
        // 院外审核结果
        List<EscortAnnexCheck> annexChecks = cache.getAnnexCheckCache().getSlaveCache()
                .get(EscortAnnexCheckCache.SlaveCache.Key.builder()
                        .cardNo(helper.getCardNo())
                        .offset(offset).build());
        // 整合结果
        List<EscortAnnexCheck> allResults = Lists.newLinkedList();
        if (lisResults != null && !lisResults.isEmpty()) {
            allResults.addAll(lisResults.stream().map(x -> {
                return EscortAnnexCheck.builder()
                        .negative(x.getResult().equals("阴性(-)"))
                        .inspectDate(x.getInspectDate()).build();
            }).toList());
        }
        if (annexChecks != null && !annexChecks.isEmpty()) {
            allResults.addAll(annexChecks);
        }
        // 逆序
        allResults.sort((x, y) -> {
            return y.getInspectDate().compareTo(x.getInspectDate());
        });
        // 判断最近的一次结果
        if (allResults != null && !allResults.isEmpty()) {
            if (allResults.get(0).getNegative()) {
                return StateResult.builder()
                        .state(EscortStateEnum.生效中)
                        .reason("核酸结果为阴性").build();
            } else {
                return StateResult.builder()
                        .state(EscortStateEnum.其他)
                        .reason("核酸结果为阳性").build();
            }
        }

        // 判断是否有院内核酸请求
        List<FinOpbFeeDetail> feeDetails = cache.getFeeDetailCache().get(FinOpbFeeDetailCache.Key.builder()
                .cardNo(helper.getCardNo())
                .itemCode("F00000068231").build());
        if (feeDetails != null && !feeDetails.isEmpty()) {
            // 按照操作时间排序
            feeDetails.sort((x, y) -> {
                return y.getOperDate().compareTo(x.getOperDate());
            });
            var duration = Duration.between(feeDetails.get(0).getOperDate(), LocalDateTime.now());
            if (duration.compareTo(Duration.ofDays(14)) < 0) {
            }
            return StateResult.builder()
                    .state(EscortStateEnum.等待院内核酸检测结果)
                    .reason("无核酸检测结果, 14天内开立过核酸项目").build();
        }

        // 判断是否有未审核的院外结果
        List<EscortAnnexInfo> annexInfos = cache.getAnnexInfoCache().getSlaveCache()
                .get(EscortAnnexInfoCache.SlaveCache.Key.builder()
                        .cardNo(helper.getCardNo())
                        .checked(false).build());
        if (annexInfos != null && !annexInfos.isEmpty()) {
            return StateResult.builder()
                    .state(EscortStateEnum.等待院外核酸检测结果审核)
                    .reason("无核酸检测结果，已上传院外结果").build();
        }

        return StateResult.builder()
                .state(EscortStateEnum.无核酸检测结果)
                .reason("无核酸结果").build();
    }

    /**
     * 强陪护状态判断
     * 
     * @param escortMainInfo
     * @return
     */
    private StateResult getStrongEscortState(EscortMainInfo escortMainInfo) {
        // 获取住院实体
        List<FinIprInMainInfo> inMainInfos = cache.getInMainInfoCache().getSlaveCache()
                .get(FinIprInMainInfoCache.SlaveCache.Key.builder()
                        .cardNo(escortMainInfo.getPatientCardNo())
                        .happenNo(escortMainInfo.getHappenNo()).build());

        // 创建在院列表和出院列表
        List<FinIprInMainInfo> inList = Lists.newArrayList();
        List<FinIprInMainInfo> outList = Lists.newArrayList();
        for (FinIprInMainInfo item : inMainInfos) {
            switch (item.getInState()) {
                case 住院登记:
                case 病房接诊:
                    inList.add(item);
                    break;

                default:
                    outList.add(item);
                    break;
            }
        }
        // 若存在在院记录
        if (!inList.isEmpty()) {
            return getStateCore(escortMainInfo);
        } else {
            // 按出院时间逆序
            outList.sort((x, y) -> {
                return y.getOutDate().compareTo(x.getOutDate());
            });
            // 判断最后一次出院记录的状态
            Duration duration = Duration.between(outList.get(0).getOutDate(), LocalDateTime.now());
            switch (outList.get(0).getInState()) {
                case 出院结算:
                case 无费退院:
                    if (DurationUtils.compare(duration, Duration.ofHours(6)) > 0) {
                        return StateResult.builder()
                                .state(EscortStateEnum.注销)
                                .reason("出院已超过6小时").build();
                    }
                    break;

                case 出院登记:
                    if (DurationUtils.compare(duration, Duration.ofHours(12)) > 0) {
                        return StateResult.builder()
                                .state(EscortStateEnum.注销)
                                .reason("出院登记已超过12小时").build();
                    }

                default:
                    break;
            }
            return getStateCore(escortMainInfo);
        }
    }

    /**
     * 若陪护状态判断
     * 
     * @param escortMainInfo
     * @return
     */
    private StateResult getWeakEscortState(EscortMainInfo escortMainInfo) {
        // 检索患者住院证列表
        var prepayIns = prepayInMapper.queryPrepayIns(FinIprPrepayInMapper.Key.builder()
                .cardNo(escortMainInfo.getPatientCardNo()).build());
        if (prepayIns == null || prepayIns.isEmpty()) {
            return StateResult.builder()
                    .state(EscortStateEnum.注销)
                    .reason("住院证丢失").build();
        } else {
            // 按happenNo逆序
            prepayIns.sort((x, y) -> {
                return IntegerUtils.compare(y.getHappenNo(), x.getHappenNo());
            });
            // 是否为最新的住院证
            if (!escortMainInfo.getHappenNo().equals(prepayIns.get(0).getHappenNo())) {
                return StateResult.builder()
                        .state(EscortStateEnum.注销)
                        .reason("已开立新住院证").build();
            }
            switch (prepayIns.get(0).getState()) {
                case 作废 -> {
                    return StateResult.builder()
                            .state(EscortStateEnum.注销)
                            .reason("住院证作废").build();
                }

                default -> {
                    return getStateCore(escortMainInfo);
                }
            }
        }
    }

    /**
     * 查询陪护证的实时状态
     * 
     * @param escortNo 陪护证编号
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public StateResult getState(EscortMainInfo escortMainInfo) {
        // 校验陪护证
        if (escortMainInfo == null) {
            log.error("试图获取一个不存在的陪护证的状态");
            throw new RuntimeException("试图获取一个不存在的陪护证的状态");
        }

        // 检索现有状态清单
        List<EscortStateRec> states = cache.getEscortStateRecCache().get(escortMainInfo.getEscortNo());
        if (states != null && !states.isEmpty()) {
            // 逆序
            states.sort((x, y) -> {
                return y.getRecNo().compareTo(x.getRecNo());
            });
            // 若已注销，则不操作
            if (states.get(0).getState() == EscortStateEnum.注销) {
                return StateResult.builder()
                        .state(EscortStateEnum.注销)
                        .reason("陪护证已注销, 不再判断状态").build();
            }
        }

        // 检索关联的住院患者实体
        List<FinIprInMainInfo> inMainInfos = cache.getInMainInfoCache().getSlaveCache()
                .get(FinIprInMainInfoCache.SlaveCache.Key.builder()
                        .cardNo(escortMainInfo.getPatientCardNo())
                        .happenNo(escortMainInfo.getHappenNo()).build());
        // 若存在关联的住院实体，则说明是强陪护
        if (inMainInfos != null && !inMainInfos.isEmpty()) {
            return getStrongEscortState(escortMainInfo);
        } else {
            return getWeakEscortState(escortMainInfo);
        }
    }

    @Getter
    @Builder
    public static class StateResult {
        /**
         * 判断的状态
         */
        private EscortStateEnum state;

        /**
         * 做出判断的原因
         */
        private String reason;
    }

    /**
     * 判断是否可以注册陪护证
     * 
     * @param patientCardNo 患者卡号
     * @param helperCardNo  陪护卡号
     */
    private void canRegister(String patientCardNo, String helperCardNo, Boolean escape) {
        // 自陪护检查
        if (patientCardNo.equals(helperCardNo)) {
            log.error(String.format("不可以为自己陪护(cardNo = %s)", patientCardNo));
            throw new RuntimeException("不可以为自己陪护");
        }

        // 已陪护检查
        List<EscortMainInfo> escortInfos = mainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .patientCardNos(Lists.newArrayList(patientCardNo))
                .helperCardNo(helperCardNo).build());
        if (escortInfos != null && !escortInfos.isEmpty()) {
            List<EscortMainInfo> activeList = Lists.newArrayList();
            List<EscortMainInfo> inactiveList = Lists.newArrayList();
            for (var escortInfo : escortInfos) {
                // 获取当前状态
                var states = cache.getEscortStateRecCache().get(escortInfo.getEscortNo());
                if (states == null || states.isEmpty()) {
                    log.error(String.format("陪护关系(空状态)已绑定(escortNo = %s)", escortInfo.getEscortNo()));
                    throw new RuntimeException("陪护关系(空状态)已绑定");
                } else {
                    switch (states.get(0).getState()) {
                        case 注销 -> {
                            inactiveList.add(escortInfo);
                        }

                        default -> {
                            activeList.add(escortInfo);
                        }
                    }
                }
            }
            // 上述循环已确保了无空状态
            if (!activeList.isEmpty()) {
                // 尚存有效陪护
                log.error(String.format("陪护关系已绑定(escortNo = %s)", gson.toJson(activeList.stream().map((x) -> {
                    return x.getEscortNo();
                }).toList())));
                throw new RuntimeException("陪护关系已绑定");
            } else {
                // 若未逃逸，判断最后一次注销的时间
                if (escape == null || !escape) {
                    inactiveList.sort((x, y) -> {
                        var xStates = cache.getEscortStateRecCache().get(x.getEscortNo());
                        var yStates = cache.getEscortStateRecCache().get(y.getEscortNo());
                        return yStates.get(0).getRecDate().compareTo(xStates.get(0).getRecDate());
                    });
                    var state = cache.getEscortStateRecCache().get(inactiveList.get(0).getEscortNo());
                    var duration = Duration.between(state.get(0).getRecDate(), LocalDateTime.now());
                    var guard = Duration.ofHours(12);
                    if (duration.compareTo(guard) < 0) {
                        // 计算差额
                        Duration offset = guard.minus(duration);
                        String errStr = durationToStringConverter.convert(offset);
                        log.error(String.format("注销后12小时内无法再次登记为陪护, 剩余%s", errStr));
                        throw new RuntimeException(String.format("注销后12小时内无法再次登记为陪护, 剩余%s", errStr));
                    }
                }
            }
        }

        // 患者陪护上限检查
        List<EscortMainInfo> patientEscorts = mainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .patientCardNos(Lists.newArrayList(patientCardNo))
                .states(Lists.newArrayList(EscortStateEnum.无核酸检测结果,
                        EscortStateEnum.等待院内核酸检测结果,
                        EscortStateEnum.等待院外核酸检测结果审核,
                        EscortStateEnum.生效中,
                        EscortStateEnum.其他))
                .build());
        if (patientEscorts != null && patientEscorts.size() >= 2) {
            log.error(String.format("患者的陪护人数量达到上限"));
            throw new RuntimeException("患者的陪护人数量达到上限");
        }

        // 陪护人陪护上限检查
        List<EscortMainInfo> helperEscorts = mainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .helperCardNo(helperCardNo)
                .states(Lists.newArrayList(EscortStateEnum.无核酸检测结果,
                        EscortStateEnum.等待院内核酸检测结果,
                        EscortStateEnum.等待院外核酸检测结果审核,
                        EscortStateEnum.生效中,
                        EscortStateEnum.其他))
                .build());
        if (helperEscorts != null && helperEscorts.size() >= 2) {
            log.error(String.format("陪护人的陪护证数量达到上限"));
            throw new RuntimeException("患者的陪护证数量达到上限");
        }
    }

    /**
     * 判断是否可以注册
     * 
     * @param patientCardNo
     * @return
     */
    private Integer getHappenNo(String patientCardNo) {
        // 检索在院记录
        List<FinIprInMainInfo> inMainInfos = cache.getInMainInfoCache().getSlaveCache()
                .get(FinIprInMainInfoCache.SlaveCache.Key.builder()
                        .cardNo(patientCardNo)
                        .states(Lists.newArrayList(InStateEnum.住院登记, InStateEnum.病房接诊))
                        .build());
        inMainInfos = inMainInfos.stream().filter((x) -> {
            return x.getHappenNo() != null;
        }).toList();
        if (inMainInfos != null && !inMainInfos.isEmpty()) {
            if (inMainInfos.size() > 1) {
                log.error(String.format("存在多条在院记录，无法确定陪护证关联(patientNo = %s)", inMainInfos.stream().map((x) -> {
                    return x.getPatientNo();
                }).toList()));
                throw new RuntimeException("存在多条在院记录，无法确定陪护证关联");
            } else {
                return inMainInfos.get(0).getHappenNo();
            }
        } else {
            // 检索最后一张住院证
            var prepayIns = prepayInMapper.queryPrepayIns(FinIprPrepayInMapper.Key.builder()
                    .cardNo(patientCardNo).build());
            if (prepayIns != null && !prepayIns.isEmpty()) {
                // 按时间逆序
                prepayIns.sort((x, y) -> {
                    return IntegerUtils.compare(y.getHappenNo(), x.getHappenNo());
                });
                switch (prepayIns.get(0).getState()) {
                    case 作废 -> {
                        log.error(String.format("最新的住院证已作废(happenNo = %d)", prepayIns.get(0).getHappenNo()));
                        throw new RuntimeException("最新的住院证已作废");
                    }

                    default -> {
                        return prepayIns.get(0).getHappenNo();
                    }
                }
            } else {
                log.error(String.format("无有效陪护证"));
                throw new RuntimeException("无有效陪护证");
            }
        }
    }

    /**
     * 模拟注册
     * 
     * @param patientCardNo
     * @param helperCardNo
     * @param escape
     * @return
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public SimulateResult simulateRegister(String patientCardNo, String helperCardNo, Boolean escape) {
        // 先做基本判断
        canRegister(patientCardNo, helperCardNo, escape);

        // 获取待关联的陪护证
        Integer happenNo = getHappenNo(patientCardNo);
        if (happenNo == null) {
            log.error(String.format("无有效陪护证"));
            throw new RuntimeException("无有效陪护证");
        }

        // 构造模拟插入的陪护证
        EscortMainInfo mainInfo = EscortMainInfo.builder()
                .escortNo("virtualNo")
                .patientCardNo(patientCardNo)
                .happenNo(happenNo)
                .helperCardNo(helperCardNo).build();

        // 获取状态
        var state = getState(mainInfo);
        if (state.getState() == EscortStateEnum.注销) {
            throw new RuntimeException(state.getReason());
        }

        return SimulateResult.builder().mainInfo(mainInfo).stateResult(state).build();
    }

    @Getter
    @Builder
    public static class SimulateResult {
        /**
         * 主记录
         */
        private EscortMainInfo mainInfo;

        /**
         * 状态
         */
        private StateResult stateResult;
    }
}
