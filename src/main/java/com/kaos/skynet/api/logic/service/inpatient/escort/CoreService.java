package com.kaos.skynet.api.logic.service.inpatient.escort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.kaos.skynet.api.data.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.cache.inpatient.FinIprInMainInfoCache;
import com.kaos.skynet.api.data.cache.inpatient.FinIprPrepayInCache;
import com.kaos.skynet.api.data.cache.inpatient.escort.EscortStateRecCache;
import com.kaos.skynet.api.data.cache.inpatient.escort.annex.EscortAnnexCheckCache;
import com.kaos.skynet.api.data.cache.inpatient.escort.annex.EscortAnnexInfoCache;
import com.kaos.skynet.api.data.cache.outpatient.fee.FinOpbFeeDetailCache;
import com.kaos.skynet.api.data.cache.pipe.lis.LisResultNewCache;
import com.kaos.skynet.api.data.entity.common.ComPatientInfo;
import com.kaos.skynet.api.data.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.FinIprPrepayIn;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexCheck;
import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexInfo;
import com.kaos.skynet.api.data.entity.outpatient.fee.FinOpbFeeDetail;
import com.kaos.skynet.api.data.entity.pipe.lis.LisResultNew;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.enums.inpatient.FinIprPrepayInStateEnum;
import com.kaos.skynet.api.enums.inpatient.InStateEnum;
import com.kaos.skynet.api.enums.inpatient.escort.EscortStateEnum;
import com.kaos.skynet.core.Gsons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
class CoreService {
    /**
     * 陪护主接口
     */
    @Autowired
    EscortMainInfoMapper mainInfoMapper;

    /**
     * 状态缓存
     */
    @Autowired
    EscortStateRecCache stateRecCache;

    /**
     * 附件缓存
     */
    @Autowired
    EscortAnnexInfoCache.SlaveCache annexInfoSlaveCache;

    /**
     * 审核缓存
     */
    @Autowired
    EscortAnnexCheckCache.SlaveCache annexCheckSlaveCache;

    /**
     * 住院证缓存
     */
    @Autowired
    FinIprPrepayInCache prepayInCache;

    /**
     * 住院证接口
     */
    @Autowired
    FinIprPrepayInMapper prepayInMapper;

    /**
     * 住院证缓存
     */
    @Autowired
    FinIprInMainInfoCache.SlaveCache inMainInfoSlaveCache;

    /**
     * 患者信息缓存
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * LIS结果检索功能
     */
    @Autowired
    LisResultNewCache lisResultNewCache;

    /**
     * 门诊划价收费接口
     */
    @Autowired
    FinOpbFeeDetailCache feeDetailCache;

    /**
     * 序列化工具
     */
    final Gson gson = Gsons.newGson();

    /**
     * 逻辑核心 - 判断陪护证状态
     * 
     * @param escortMainInfo 陪护主表信息
     * @return
     */
    private StateResult getStateCore(EscortMainInfo escortMainInfo) {
        // 获取陪护人的信息实体
        ComPatientInfo helper = patientInfoCache.get(escortMainInfo.getHelperCardNo());
        if (helper == null) {
            return new StateResult() {
                {
                    setState(EscortStateEnum.注销);
                    setReason(String.format("陪护人不存在(cardNo = %s)", escortMainInfo.getHelperCardNo()));
                }
            };
        }

        // 判断陪护人的健康码
        if (helper.getHealthCode() != null) {
            switch (helper.getHealthCode()) {
                case 绿码 -> {
                    // 通过此项判断, 不做任何事
                }
                default -> {
                    return new StateResult() {
                        {
                            setState(EscortStateEnum.其他);
                            setReason(String.format("陪护人健康码异常(%s)", helper.getHealthCode().getDescription()));
                        }
                    };
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
                    return new StateResult() {
                        {
                            setState(EscortStateEnum.其他);
                            setReason(String.format("陪护人行程码异常(%s)", helper.getTravelCode().getDescription()));
                        }
                    };
                }
            }
        }

        // 高风险地区标识判断
        if (helper.getHighRiskFlag() != null) {
            if (helper.getHighRiskFlag()) {
                return new StateResult() {
                    {
                        setState(EscortStateEnum.其他);
                        setReason(String.format("陪护人经过了高风险地区(%s)", helper.getHighRiskArea()));
                    }
                };
            }
        }

        // 决定有效核酸结果有效期
        Integer ptr = 0;
        List<EscortStateRec> stateRecs = stateRecCache.get(escortMainInfo.getEscortNo());
        if (stateRecs != null && !stateRecs.isEmpty() && stateRecs.get(0).getState() == EscortStateEnum.生效中) {
            ptr = 2;
        } else {
            ptr = 14;
        }
        final Integer offset = ptr;

        // 本院核酸结果
        List<LisResultNew> lisResults = lisResultNewCache.get(new LisResultNewCache.Key() {
            {
                setPatientId(helper.getCardNo());
                setItemCodes(Lists.newArrayList("SARS-CoV-2-RNA"));
                setOffset(offset);
            }
        });
        // 院外审核结果
        List<EscortAnnexCheck> annexChecks = annexCheckSlaveCache.get(new EscortAnnexCheckCache.Key() {
            {
                setCardNo(helper.getCardNo());
                setOffset(offset);
            }
        });
        // 整合结果
        List<EscortAnnexCheck> allResults = Lists.newLinkedList();
        if (lisResults != null && !lisResults.isEmpty()) {
            allResults.addAll(lisResults.stream().map((x) -> {
                return new EscortAnnexCheck() {
                    {
                        setNegative(x.getResult().equals("阴性(-)"));
                        setInspectDate(x.getInspectDate());
                    }
                };
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
                return new StateResult() {
                    {
                        setState(EscortStateEnum.生效中);
                        setReason("核酸结果为阴性");
                    }
                };
            } else {
                return new StateResult() {
                    {
                        setState(EscortStateEnum.其他);
                        setReason("核酸结果为阳性");
                    }
                };
            }
        }

        // 判断是否有院内核酸请求
        List<FinOpbFeeDetail> feeDetails = feeDetailCache.get(new FinOpbFeeDetailCache.Key() {
            {
                setCardNo(helper.getCardNo());
                setItemCode("F00000068231");
                setOffset(offset);
            }
        });
        if (feeDetails != null && !feeDetails.isEmpty()) {
            return new StateResult() {
                {
                    setState(EscortStateEnum.等待院内核酸检测结果);
                    setReason("无核酸检测结果，已开核酸检测");
                }
            };
        }

        // 判断是否有未审核的院外结果
        List<EscortAnnexInfo> annexInfos = annexInfoSlaveCache.get(new EscortAnnexInfoCache.SlaveCache.Key() {
            {
                setCardNo(helper.getCardNo());
                setChecked(false);
            }
        });
        if (annexInfos != null && !annexInfos.isEmpty()) {
            return new StateResult() {
                {
                    setState(EscortStateEnum.等待院外核酸检测结果审核);
                    setReason("无核酸检测结果，已上传院外结果");
                }
            };
        }

        return new StateResult() {
            {
                setState(EscortStateEnum.无核酸检测结果);
                setReason("无核酸结果");
            }
        };
    }

    /**
     * 强陪护状态判断
     * 
     * @param escortMainInfo
     * @return
     */
    private StateResult getStrongEscortState(EscortMainInfo escortMainInfo) {
        // 获取住院实体
        List<FinIprInMainInfo> inMainInfos = inMainInfoSlaveCache.get(new FinIprInMainInfoCache.SlaveCache.Key() {
            {
                setCardNo(escortMainInfo.getPatientCardNo());
                setHappenNo(escortMainInfo.getHappenNo());
            }
        });

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
                    if (duration.toHours() >= 6) {
                        return new StateResult() {
                            {
                                setState(EscortStateEnum.注销);
                                setReason("出院已超过6小时");
                            }
                        };
                    }
                    break;

                case 出院登记:
                    if (duration.toHours() >= 12) {
                        return new StateResult() {
                            {
                                setState(EscortStateEnum.注销);
                                setReason("出院登记已超过12小时");
                            }
                        };
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
        // 检索陪护证关联的住院证
        FinIprPrepayIn prepayIn = prepayInCache.get(new FinIprPrepayInCache.Key() {
            {
                setCardNo(escortMainInfo.getPatientCardNo());
                setHappenNo(escortMainInfo.getHappenNo());
            }
        });
        if (prepayIn == null) {
            return new StateResult() {
                {
                    setState(EscortStateEnum.注销);
                    setReason("住院证丢失");
                }
            };
        } else {
            switch (prepayIn.getState()) {
                case 作废 -> {
                    return new StateResult() {
                        {
                            setState(EscortStateEnum.注销);
                            setReason("住院证作废");
                        }
                    };
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
    public StateResult getState(EscortMainInfo escortMainInfo) {
        // 校验陪护证
        if (escortMainInfo == null) {
            log.error("试图获取一个不存在的陪护证的状态");
            throw new RuntimeException("试图获取一个不存在的陪护证的状态");
        }

        // 检索现有状态清单
        List<EscortStateRec> states = stateRecCache.get(escortMainInfo.getEscortNo());
        if (states != null && !states.isEmpty()) {
            // 逆序
            states.sort((x, y) -> {
                return y.getRecNo().compareTo(x.getRecNo());
            });
            // 若已注销，则不操作
            if (states.get(0).getState() == EscortStateEnum.注销) {
                return new StateResult() {
                    {
                        setState(EscortStateEnum.注销);
                        setReason("陪护证已注销, 不再判断状态");
                    }
                };
            }
        }

        // 检索关联的住院患者实体
        List<FinIprInMainInfo> inMainInfos = inMainInfoSlaveCache.get(new FinIprInMainInfoCache.SlaveCache.Key() {
            {
                setCardNo(escortMainInfo.getPatientCardNo());
                setHappenNo(escortMainInfo.getHappenNo());
            }
        });
        // 若存在关联的住院实体，则说明是强陪护
        if (inMainInfos != null && !inMainInfos.isEmpty()) {
            return getStrongEscortState(escortMainInfo);
        } else {
            return getWeakEscortState(escortMainInfo);
        }
    }

    @Data
    public static class StateResult {
        /**
         * 判断的状态
         */
        private EscortStateEnum state = null;

        /**
         * 做出判断的原因
         */
        private String reason = null;
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
        List<EscortMainInfo> escortInfos = mainInfoMapper.queryEscortMainInfos(new EscortMainInfoMapper.Key() {
            {
                setPatientCardNo(patientCardNo);
                setHelperCardNo(helperCardNo);
            }
        });
        if (escortInfos != null && !escortInfos.isEmpty()) {
            List<EscortMainInfo> activeList = Lists.newArrayList();
            List<EscortMainInfo> inactiveList = Lists.newArrayList();
            for (var escortInfo : escortInfos) {
                // 获取当前状态
                var states = stateRecCache.get(escortInfo.getEscortNo());
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
                        var xStates = stateRecCache.get(x.getEscortNo());
                        var yStates = stateRecCache.get(y.getEscortNo());
                        return yStates.get(0).getRecDate().compareTo(xStates.get(0).getRecDate());
                    });
                    var state = stateRecCache.get(inactiveList.get(0).getEscortNo());
                    var duration = Duration.between(state.get(0).getRecDate(), LocalDateTime.now());
                    if (duration.toHours() < 12) {
                        log.error(String.format("注销后12小时内无法再次登记为陪护"));
                        throw new RuntimeException("注销后12小时内无法再次登记为陪护");
                    }
                }
            }
        }

        // 患者陪护上限检查
        List<EscortMainInfo> patientEscorts = mainInfoMapper.queryEscortMainInfos(new EscortMainInfoMapper.Key() {
            {
                setPatientCardNo(patientCardNo);
                setStates(new ArrayList<>() {
                    {
                        add(EscortStateEnum.无核酸检测结果);
                        add(EscortStateEnum.等待院内核酸检测结果);
                        add(EscortStateEnum.等待院外核酸检测结果审核);
                        add(EscortStateEnum.生效中);
                        add(EscortStateEnum.其他);
                    }
                });
            }
        });
        if (patientEscorts != null && patientEscorts.size() >= 2) {
            log.error(String.format("患者的陪护人数量达到上限"));
            throw new RuntimeException("患者的陪护人数量达到上限");
        }

        // 陪护人陪护上限检查
        List<EscortMainInfo> helperEscorts = mainInfoMapper.queryEscortMainInfos(new EscortMainInfoMapper.Key() {
            {
                setHelperCardNo(helperCardNo);
                setStates(new ArrayList<>() {
                    {
                        add(EscortStateEnum.无核酸检测结果);
                        add(EscortStateEnum.等待院内核酸检测结果);
                        add(EscortStateEnum.等待院外核酸检测结果审核);
                        add(EscortStateEnum.生效中);
                        add(EscortStateEnum.其他);
                    }
                });
            }
        });
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
        List<FinIprInMainInfo> inMainInfos = inMainInfoSlaveCache.get(new FinIprInMainInfoCache.SlaveCache.Key() {
            {
                setCardNo(patientCardNo);
                setStates(new ArrayList<>() {
                    {
                        add(InStateEnum.住院登记);
                        add(InStateEnum.病房接诊);
                    }
                });
            }
        });
        inMainInfos = inMainInfos.stream().filter((x) -> {
            return x.getHappenNo() != null;
        }).toList();
        if (inMainInfos != null && inMainInfos.isEmpty()) {
            if (inMainInfos.size() > 1) {
                log.error(String.format("存在多条在院记录，无法确定陪护证关联(patientNo = %s)", inMainInfos.stream().map((x) -> {
                    return x.getPatientNo();
                }).toList()));
                throw new RuntimeException("存在多条在院记录，无法确定陪护证关联");
            } else {
                return inMainInfos.get(0).getHappenNo();
            }
        } else {
            // 检索最后一张有效的住院证
            var prepayIns = prepayInMapper.queryPrepayIns(new FinIprPrepayInMapper.Key() {
                {
                    setCardNo(patientCardNo);
                    setStates(new ArrayList<>() {
                        {
                            add(FinIprPrepayInStateEnum.预约);
                            add(FinIprPrepayInStateEnum.转住院);
                            add(FinIprPrepayInStateEnum.签床);
                            add(FinIprPrepayInStateEnum.预住院预约);
                        }
                    });
                }
            });
            if (prepayIns != null && !prepayIns.isEmpty()) {
                // 按时间逆序
                prepayIns.sort((x, y) -> {
                    return y.getPreDate().compareTo(x.getPreDate());
                });
                return prepayIns.get(0).getHappenNo();
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
        EscortMainInfo mainInfo = new EscortMainInfo() {
            {
                setEscortNo(null);
                setPatientCardNo(patientCardNo);
                setHappenNo(happenNo);
                setHelperCardNo(helperCardNo);
            }
        };

        // 获取状态
        var state = getState(mainInfo);
        if (state.getState() == EscortStateEnum.注销) {
            throw new RuntimeException(state.getReason());
        }

        return new SimulateResult() {
            {
                setMainInfo(mainInfo);
                setState(state.getState());
            }
        };
    }

    @Data
    public static class SimulateResult {
        /**
         * 主记录
         */
        private EscortMainInfo mainInfo = null;

        /**
         * 状态
         */
        private EscortStateEnum state = null;
    }
}
