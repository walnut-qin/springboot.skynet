package com.kaos.his.service.inpatient.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.kaos.his.entity.inpatient.FinIprPrepayIn;
import com.kaos.his.entity.inpatient.Inpatient;
import com.kaos.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.his.entity.inpatient.escort.EscortStateRec;
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
import com.kaos.his.mapper.inpatient.order.MetOrdiOrderMapper;
import com.kaos.his.mapper.outpatient.fee.FinOpbFeeDetailMapper;
import com.kaos.his.mapper.pipe.lis.LisResultNewMapper;
import com.kaos.his.service.inpatient.EscortService;
import com.kaos.util.ListHelper;

import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;
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
     * 住院医嘱接口
     */
    @Autowired
    MetOrdiOrderMapper metOrdiOrderMapper;

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
                throw new RuntimeException(String.format("陪护证(%s)关联的住院证不存在，无法判断状态", context.escortNo));
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
        if (inps.size() >= 2) {
            // 过滤出院记录
            inps = Collections2.filter(inps, new Predicate<Inpatient>() {
                @Override
                public boolean apply(@Nullable Inpatient input) {
                    switch (input.inState) {
                        case 出院结算:
                        case 无费退院:
                            return false;

                        default:
                            return true;
                    }
                }
            }).stream().toList();
        }
        switch (inps.size()) {
            // 弱陪护
            case 0:
                var lastFip = this.finIprPrepayInMapper.queryLastPrepayIn(fip.cardNo, null);
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
                throw new RuntimeException(String.format("住院证(%s)存在多张关联的有效住院实体，无法判断状态", context.escortNo));
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
     * 判断能否注册陪护
     * 
     * @param patientCardNo
     * @param helperCardNo
     */
    private void canRegister(FinIprPrepayIn fip, String helperCardNo) throws RuntimeException {
        // 判断0：自陪护
        if (fip.cardNo.equals(helperCardNo)) {
            throw new RuntimeException("不可以给自己陪护");
        }

        // 判断1：已陪护 || 刚注销
        var lastEscort = this.escortMainInfoMapper.queryLastEscortMainInfo(fip.cardNo, null, helperCardNo, null);
        var curState = ListHelper.GetLast(lastEscort.associateEntity.stateRecs);
        if (curState.state != EscortStateEnum.注销) {
            throw new RuntimeException("陪护关系已绑定，请勿重复绑定");
        } else {
            var offset = new Date().getTime() - curState.recDate.getTime();
            if (offset <= 12 * 60 * 60 * 1000) {
                var hour = offset / (1000 * 60 * 60);
                var mins = (offset - hour * 60 * 60 * 1000) / (1000 * 60);
                var secs = (offset - hour * 60 * 60 * 1000 - mins * 60 * 1000) / 1000;
                throw new RuntimeException(String.format("注销12小时后才能重新绑定，剩余%s%s%s", hour > 0 ? hour + "小时" : "",
                        mins > 0 ? mins + "分" : "", secs > 0 ? secs + "秒" : ""));
            }
        }

        // 判断2：患者陪护上限
        var patientEscorts = this.escortMainInfoMapper.queryEscortMainInfos(fip.cardNo, null, null,
                new ArrayList<>() {
                    {
                        add(EscortStateEnum.无核酸检测结果);
                        add(EscortStateEnum.等待院内核酸检测结果);
                        add(EscortStateEnum.等待院外核酸检测结果审核);
                        add(EscortStateEnum.生效中);
                    }
                });
        switch (patientEscorts.size()) {
            case 0:
                break;

            case 1:
                if (fip.associateEntity.patient != null && fip.associateEntity.patient instanceof Inpatient) {
                    Inpatient inp = (Inpatient) fip.associateEntity.patient;
                    var ords = this.metOrdiOrderMapper.queryInpatientOrders(inp.inpatientNo, "5070672", null, null);
                    if (ords != null && !ords.isEmpty()) {
                        break;
                    }
                }
                throw new RuntimeException("未开立第二陪护医嘱，无法添加更多陪护");

            default:
                throw new RuntimeException("人数已满，无法添加更多陪护");
        }

        // 判断3：陪护人上限
        var helperEscorts = this.escortMainInfoMapper.queryEscortMainInfos(null, null, helperCardNo,
                new ArrayList<>() {
                    {
                        add(EscortStateEnum.无核酸检测结果);
                        add(EscortStateEnum.等待院内核酸检测结果);
                        add(EscortStateEnum.等待院外核酸检测结果审核);
                        add(EscortStateEnum.生效中);
                    }
                });
        if (helperEscorts.size() >= 2) {
            throw new RuntimeException("人数已满，无法陪护更多患者");
        }
    }

    /**
     * 登记陪护证
     */
    @Transactional
    @Override
    public EscortMainInfo registerEscort(String patientCardNo, String helperCardNo, String emplCode, String remark) {
        // 查询有效的住院记录
        var inps = this.inpatientMapper.queryInpatients(patientCardNo, null, new ArrayList<>() {
            {
                add(InpatientStateEnum.住院登记);
                add(InpatientStateEnum.病房接诊);
            }
        });
        FinIprPrepayIn fip = null;
        if (inps == null || inps.size() == 0) {
            fip = this.finIprPrepayInMapper.queryLastPrepayIn(patientCardNo, new ArrayList<>() {
                {
                    add(FinIprPrepayInStateEnum.预约);
                    add(FinIprPrepayInStateEnum.转住院);
                    add(FinIprPrepayInStateEnum.签床);
                    add(FinIprPrepayInStateEnum.预住院预约);
                }
            });
            if (fip == null) {
                throw new RuntimeException(String.format("患者(%s)无有效住院证，无法判断关联数据", patientCardNo));
            }
        } else if (inps.size() == 1) {
            var inp = inps.get(0);
            fip = this.finIprPrepayInMapper.queryPrepayIn(inp.cardNo, inp.happenNo);
            if (fip == null) {
                throw new RuntimeException(String.format("患者(%s)住院记录(%s)未关联住院证，无法判断关联数据", inp.patientNo));
            }
            fip.associateEntity.patient = inp;
        } else {
            throw new RuntimeException(String.format("患者(%s)存在多条住院记录，无法判断关联数据", patientCardNo));
        }

        // 权限判断
        this.canRegister(fip, helperCardNo);

        // 创建新陪护实体
        var escort = new EscortMainInfo();
        escort.escortNo = null;
        escort.patientCardNo = fip.cardNo;
        escort.happenNo = fip.happenNo;
        escort.helperCardNo = helperCardNo;
        escort.remark = "";

        // 插入陪护证主表
        this.escortMainInfoMapper.insertEscortMainInfo(escort);

        // 获取陪护证实时状态
        var stateEnum = this.queryRealState(escort);
        if (stateEnum == EscortStateEnum.注销) {
            throw new RuntimeException("陪护证注册成功，但判断其状态已注销");
        } else {
            // 创建状态实体
            var state = new EscortStateRec();
            state.escortNo = escort.escortNo;
            state.recNo = null;
            state.state = stateEnum;
            state.recEmplCode = emplCode;
            state.recDate = new Date();
            state.remark = remark;

            // 插入状态记录
            this.escortStateRecMapper.insertState(state);

            // 关联实体
            escort.associateEntity.stateRecs = new ArrayList<>();
            escort.associateEntity.stateRecs.add(state);
        }

        return escort;
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
        return null;
    }
}
