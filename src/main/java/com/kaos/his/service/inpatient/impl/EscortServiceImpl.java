package com.kaos.his.service.inpatient.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kaos.his.entity.inpatient.FinIprPrepayIn;
import com.kaos.his.entity.inpatient.Inpatient;
import com.kaos.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.enums.FinIprPrepayInStateEnum;
import com.kaos.his.enums.InpatientStateEnum;
import com.kaos.his.mapper.common.PatientMapper;
import com.kaos.his.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.his.mapper.inpatient.InpatientMapper;
import com.kaos.his.mapper.inpatient.escort.EscortActionRecMapper;
import com.kaos.his.mapper.inpatient.escort.EscortAnnexChkMapper;
import com.kaos.his.mapper.inpatient.escort.EscortAnnexInfoMapper;
import com.kaos.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.his.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.his.mapper.outpatient.fee.FinOpbFeeDetailMapper;
import com.kaos.his.mapper.pipe.lis.LisResultNewMapper;
import com.kaos.his.service.inpatient.EscortService;
import com.kaos.util.ListHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EscortServiceImpl implements EscortService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(DayReportServiceImpl.class.getName());

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
     * 陪护证动作接口
     */
    @Autowired
    EscortActionRecMapper escortActionRecMapper;

    /**
     * 住院证接口
     */
    @Autowired
    FinIprPrepayInMapper finIprPrepayInMapper;

    /**
     * 住院接口
     */
    @Autowired
    InpatientMapper inpatientMapper;

    /**
     * 患者接口
     */
    @Autowired
    PatientMapper patientMapper;

    /**
     * 附件接口
     */
    @Autowired
    EscortAnnexInfoMapper escortAnnexInfoMapper;

    /**
     * 审核附件接口
     */
    @Autowired
    EscortAnnexChkMapper escortAnnexChkMapper;

    /**
     * 门诊划价接口
     */
    @Autowired
    FinOpbFeeDetailMapper finOpbFeeDetailMapper;

    /**
     * LIS结果查询
     */
    @Autowired
    LisResultNewMapper lisResultNewMapper;

    /**
     * 查询当前陪护证的实时状态
     * 
     * @param context 业务上下文（陪护证实体）
     * @return
     */
    private EscortStateEnum queryRealState(EscortMainInfo context) {
        // 锚定关联对象
        var ntt = context.associateEntity;

        // 当前状态是否存在
        if (ntt.stateRecs == null) {
            ntt.stateRecs = this.escortStateRecMapper.queryStates(context.escortNo);
        }
        if (ntt.stateRecs != null && !ntt.stateRecs.isEmpty()) {
            var curState = ListHelper.GetLast(ntt.stateRecs).state;
            if (curState == EscortStateEnum.注销) {
                return EscortStateEnum.注销;
            }
        }

        // 获取关联住院证
        if (ntt.finIprPrepayIn == null) {
            ntt.finIprPrepayIn = this.finIprPrepayInMapper.queryPrepayIn(context.patientCardNo, context.happenNo);
            if (ntt.finIprPrepayIn == null) {
                throw new RuntimeException(String.format("陪护证(%s)无关联的住院证，无法判断状态", context.escortNo));
            }
        }

        // 住院证有效？
        var fip = ntt.finIprPrepayIn;
        switch (fip.state) {
            case 作废:
                return EscortStateEnum.注销;

            default:
                break;
        }

        // 获取住院实体/患者实体
        var inps = this.inpatientMapper.queryInpatients(context.patientCardNo, context.happenNo, null);
        switch (inps.size()) {
            case 0:
                if (fip.associateEntity.patient == null) {
                    fip.associateEntity.patient = this.patientMapper.queryPatient(fip.cardNo);
                }
                var lastFip = this.finIprPrepayInMapper.queryLastPrepayIn(fip.cardNo, new ArrayList<>() {
                    {
                        add(FinIprPrepayInStateEnum.预约);
                        add(FinIprPrepayInStateEnum.转住院);
                        add(FinIprPrepayInStateEnum.签床);
                        add(FinIprPrepayInStateEnum.预住院预约);
                    }
                });
                if (!lastFip.happenNo.equals(fip.happenNo)) {
                    return EscortStateEnum.注销;
                }
                break;

            case 1:
                // 锚定住院证
                fip.associateEntity.patient = inps.get(0);
                var inp = (Inpatient) fip.associateEntity.patient;
                switch (inp.inState) {
                    case 出院结算:
                    case 无费退院:
                        return EscortStateEnum.注销;

                    case 出院登记:
                        if (new Date().getTime() - inp.outDate.getTime() >= 12 * 60 * 60 * 1000) {
                            return EscortStateEnum.注销;
                        }
                        break;

                    default:
                        break;
                }
                break;

            default:
                throw new RuntimeException(String.format("住院证(%s)存在多张关联的住院实体，无法判断状态", context.escortNo));
        }

        // 锚定7天前的当前时间
        var calender = Calendar.getInstance();
        calender.add(Calendar.DATE, -7);
        var beginDate = calender.getTime();

        // 查询7日内本院核酸记录
        var lisRs = this.lisResultNewMapper.queryInspectResult(ntt.helper.cardNo, "SARS-CoV-2-RNA", beginDate, null);
        if (lisRs != null && !lisRs.isEmpty()) {
            var lastLisRt = lisRs.get(0);
            if (lastLisRt.result.equals("阴性(-)")) {
                return EscortStateEnum.生效中;
            }
        }

        // 查询院外核酸报告（已审核）
        var annexChks = this.escortAnnexChkMapper.queryAnnexChks(fip.cardNo, beginDate, null);
        if (annexChks != null && !annexChks.isEmpty()) {
            var lastAnnexChk = annexChks.get(0);
            if (lastAnnexChk.negativeFlag) {
                return EscortStateEnum.生效中;
            }
        }

        // 查询7日内划价记录
        var fees = this.finOpbFeeDetailMapper.queryFeeDetailsWithCardNo(fip.cardNo, "F00000068231", beginDate, null);
        if (fees != null && !fees.isEmpty()) {
            return EscortStateEnum.等待院内核酸检测结果;
        }

        // 查询7日内上传的院外待审记录
        var annexInfos = this.escortAnnexInfoMapper.queryAnnexInfos(fip.cardNo, beginDate, null, false);
        if (annexInfos != null && !annexInfos.isEmpty()) {
            return EscortStateEnum.等待院外核酸检测结果审核;
        }

        return EscortStateEnum.无核酸检测结果;
    }

    /**
     * 查询待注册陪护关联的住院证
     * 
     * @param patientCardNo
     * @return
     */
    private FinIprPrepayIn queryRegisteredPrepayIn(String patientCardNo) {
        // 查询最近的住院实体
        var lastInp = this.inpatientMapper.queryLastInpatient(patientCardNo, null);
        if (lastInp != null) {
            switch (lastInp.inState) {
                case 出院结算:
                case 无费退院:
                    return this.finIprPrepayInMapper.queryLastPrepayIn(patientCardNo, new ArrayList<>() {
                        {
                            add(FinIprPrepayInStateEnum.预约);
                            add(FinIprPrepayInStateEnum.转住院);
                            add(FinIprPrepayInStateEnum.签床);
                            add(FinIprPrepayInStateEnum.预住院预约);
                        }
                    });

                case 出院登记:
                    if (new Date().getTime() - lastInp.outDate.getTime() >= 12 * 60 * 60 * 1000) {
                        return this.finIprPrepayInMapper.queryLastPrepayIn(patientCardNo, new ArrayList<>() {
                            {
                                add(FinIprPrepayInStateEnum.预约);
                                add(FinIprPrepayInStateEnum.转住院);
                                add(FinIprPrepayInStateEnum.签床);
                                add(FinIprPrepayInStateEnum.预住院预约);
                            }
                        });
                    } else {
                        // 响应关联住院证
                        return this.finIprPrepayInMapper.queryPrepayIn(lastInp.cardNo, lastInp.happenNo);
                    }

                default:
                    // 响应关联住院证
                    return this.finIprPrepayInMapper.queryPrepayIn(lastInp.cardNo, lastInp.happenNo);
            }
        }

        // 查询最近有效的住院证

        return null;
    }

    @Transactional
    @Override
    public EscortMainInfo registerEscort(String patientCardNo, String helperCardNo) {
        // 获取目标住院证
        var fip = this.queryRegisteredPrepayIn(patientCardNo);
        if (fip == null) {
            throw new RuntimeException("无关联的住院证");
        }

        // 插入一条主表记录
        
        return null;
    }

    @Override
    public EscortMainInfo queryEscortStateInfo(String escortNo) {
        // 检索陪护证
        var rt = this.escortMainInfoMapper.queryEscortMainInfo(escortNo);
        if (rt == null) {
            return null;
        }

        // 添加关联状态清单
        rt.associateEntity.stateRecs = this.escortStateRecMapper.queryStates(escortNo);

        return rt;
    }

    @Override
    public List<EscortMainInfo> queryPatientInfos(String helperCardNo) {
        // 检索关联的陪护证实体
        var rs = this.escortMainInfoMapper.queryHelperEscortMainInfos(helperCardNo, new ArrayList<>() {
            {
                add(EscortStateEnum.无核酸检测结果);
                add(EscortStateEnum.等待院内核酸检测结果);
                add(EscortStateEnum.等待院外核酸检测结果审核);
                add(EscortStateEnum.生效中);
            }
        });

        for (var rt : rs) {
            // 填充装填实体
            rt.associateEntity.stateRecs = this.escortStateRecMapper.queryStates(rt.escortNo);

            // 填充动作实体
            rt.associateEntity.actionRecs = this.escortActionRecMapper.queryActions(rt.escortNo);

            // 填充患者信息
            rt.associateEntity.finIprPrepayIn = this.finIprPrepayInMapper.queryPrepayIn(rt.patientCardNo, rt.happenNo);
            if (rt.associateEntity.finIprPrepayIn != null) {
                // 锚定住院证
                var fip = rt.associateEntity.finIprPrepayIn;

                // 检索住院实体
                var inpatients = this.inpatientMapper.queryInpatients(rt.patientCardNo, rt.happenNo, new ArrayList<>() {
                    {
                        add(InpatientStateEnum.住院登记);
                        add(InpatientStateEnum.病房接诊);
                        add(InpatientStateEnum.出院登记);
                        add(InpatientStateEnum.预约出院);
                    }
                });
                switch (inpatients.size()) {
                    case 0:
                        fip.associateEntity.patient = this.patientMapper.queryPatient(rt.patientCardNo);
                        break;

                    case 1:
                        fip.associateEntity.patient = inpatients.get(0);
                        break;

                    default:
                        fip.associateEntity.patient = this.patientMapper.queryPatient(rt.patientCardNo);
                        break;
                }
            }
        }
        return null;
    }
}
