package com.kaos.his.service.impl.inpatient.escort;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.kaos.his.entity.common.ComPatientInfo;
import com.kaos.his.entity.inpatient.FinIprPrepayIn;
import com.kaos.his.entity.inpatient.Inpatient;
import com.kaos.his.entity.inpatient.escort.EscortActionRec;
import com.kaos.his.entity.inpatient.escort.EscortAnnexChk;
import com.kaos.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.his.entity.inpatient.escort.EscortStateRec;
import com.kaos.his.entity.inpatient.escort.EscortVip;
import com.kaos.his.enums.impl.inpatient.FinIprPrepayInStateEnum;
import com.kaos.his.enums.impl.inpatient.InStateEnum;
import com.kaos.his.enums.impl.inpatient.escort.EscortActionEnum;
import com.kaos.his.enums.impl.inpatient.escort.EscortStateEnum;
import com.kaos.his.mapper.common.DawnOrgDeptMapper;
import com.kaos.his.mapper.common.DawnOrgEmplMapper;
import com.kaos.his.mapper.common.ComPatientInfoMapper;
import com.kaos.his.mapper.inpatient.ComBedInfoMapper;
import com.kaos.his.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.his.mapper.inpatient.InpatientMapper;
import com.kaos.his.mapper.inpatient.escort.EscortActionRecMapper;
import com.kaos.his.mapper.inpatient.escort.EscortAnnexChkMapper;
import com.kaos.his.mapper.inpatient.escort.EscortAnnexInfoMapper;
import com.kaos.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.his.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.his.mapper.inpatient.escort.EscortVipMapper;
import com.kaos.his.mapper.inpatient.order.MetOrdiOrderMapper;
import com.kaos.his.mapper.outpatient.fee.FinOpbFeeDetailMapper;
import com.kaos.his.mapper.pipe.lis.LisResultNewMapper;
import com.kaos.his.service.impl.inpatient.fee.report.DayReportServiceImpl;
import com.kaos.his.service.inf.inpatient.escort.EscortService;
import com.kaos.util.helper.ListHelper;

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
     * 基本类型助手
     */
    @Autowired
    ListHelper listHelper;

    /**
     * 注解自身
     */
    @Autowired
    EscortService selfService;

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
     * vip接口
     */
    @Autowired
    EscortVipMapper escortVipMapper;

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
    ComPatientInfoMapper patientMapper;

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
     * 员工接口
     */
    @Autowired
    DawnOrgEmplMapper employeeMapper;

    /**
     * 科室接口
     */
    @Autowired
    DawnOrgDeptMapper departmentMapper;

    /**
     * 床位接口
     */
    @Autowired
    ComBedInfoMapper comBedInfoMapper;

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
            var curState = this.listHelper.getLast(ntt.stateRecs).state;
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
        var inps = this.inpatientMapper.queryInpatientsWithPrepayIn(context.patientCardNo, context.happenNo);
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
                        if (new Date().getTime() - inp.outDate.getTime() >= 6 * 60 * 60 * 1000) {
                            return EscortStateEnum.注销;
                        }
                        break;

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
                throw new RuntimeException(String.format("住院证(cardNo = %s, happenNo = %s)存在多张关联的有效住院实体，无法判断状态",
                        context.patientCardNo, context.happenNo));
        }

        // 锚定7天前的当前时间
        var calender = Calendar.getInstance();
        calender.add(Calendar.DATE, -7);
        var beginDate = calender.getTime();

        // 查询7日内本院核酸记录
        var lisRs = this.lisResultNewMapper.queryInspectResult(context.helperCardNo, "SARS-CoV-2-RNA", beginDate, null);
        if (lisRs != null && !lisRs.isEmpty()) {
            var lastLisRt = lisRs.get(0);
            if (lastLisRt.result.equals("阴性(-)")) {
                return EscortStateEnum.生效中;
            }
        }

        // 查询院外核酸报告（已审核）
        var checkedAnnexInfos = this.escortAnnexInfoMapper.queryAnnexInfos(context.helperCardNo, beginDate, null, true);
        if (checkedAnnexInfos != null && !checkedAnnexInfos.isEmpty()) {
            // 提取最近一次测试结果
            EscortAnnexChk lastRecord = null;
            for (var annexInfo : checkedAnnexInfos) {
                var curRec = this.escortAnnexChkMapper.queryAnnexChk(annexInfo.annexNo);
                if (lastRecord == null || lastRecord.inspectDate.before(curRec.inspectDate)) {
                    lastRecord = curRec;
                }
            }
            // 如果最近一次结果为阴性，则生效
            if (lastRecord.negativeFlag) {
                return EscortStateEnum.生效中;
            }
        }

        // 查询7日内划价记录
        var fees = this.finOpbFeeDetailMapper.queryFeeDetailsWithCardNo(context.helperCardNo, "F00000068231", beginDate,
                null);
        if (fees != null && !fees.isEmpty()) {
            return EscortStateEnum.等待院内核酸检测结果;
        }

        // 查询7日内上传的院外待审记录
        var unCheckedAnnexInfos = this.escortAnnexInfoMapper.queryAnnexInfos(context.helperCardNo, beginDate, null,
                false);
        if (unCheckedAnnexInfos != null && !unCheckedAnnexInfos.isEmpty()) {
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
    private void canRegister(FinIprPrepayIn fip, ComPatientInfo helper) throws RuntimeException {
        // 入参判断
        if (fip == null) {
            throw new RuntimeException("住院证不存在");
        } else if (helper == null) {
            throw new RuntimeException("陪护人不存在");
        }

        // 判断0：自陪护
        if (fip.cardNo.equals(helper.cardNo)) {
            throw new RuntimeException("不可以给自己陪护");
        }

        // 判断1：已陪护 || 刚注销
        var lastEscort = this.escortMainInfoMapper.queryLastEscortMainInfo(fip.cardNo, null, helper.cardNo, null);
        if (lastEscort != null) {
            var curState = this.escortStateRecMapper.queryCurState(lastEscort.escortNo);
            if (curState.state != EscortStateEnum.注销) {
                throw new RuntimeException("陪护关系已绑定，请勿重复绑定");
            } else {
                var offset = new Date().getTime() - curState.recDate.getTime();
                if (offset <= 12 * 60 * 60 * 1000) {
                    var rest = 12 * 60 * 60 * 1000 - offset;
                    var hour = rest / (1000 * 60 * 60);
                    var mins = (rest - hour * 60 * 60 * 1000) / (1000 * 60);
                    var secs = (rest - hour * 60 * 60 * 1000 - mins * 60 * 1000) / 1000;
                    throw new RuntimeException(String.format("注销12小时后才能重新绑定，剩余%s%s%s", hour > 0 ? hour + "小时" : "",
                            mins > 0 ? mins + "分" : "", secs > 0 ? secs + "秒" : ""));
                }
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
        var helperEscorts = this.escortMainInfoMapper.queryEscortMainInfos(null, null, helper.cardNo,
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
        // 判定住院证
        var inps = this.inpatientMapper.queryInpatients(patientCardNo, null, new ArrayList<>() {
            {
                add(InStateEnum.住院登记);
                add(InStateEnum.病房接诊);
            }
        });
        FinIprPrepayIn fip = null;
        if (inps == null || inps.size() == 0) {
            // 弱陪护，直接取最后一张住院证
            fip = this.finIprPrepayInMapper.queryLastPrepayIn(patientCardNo, null);
            if (fip == null || fip.state == FinIprPrepayInStateEnum.作废) {
                throw new RuntimeException(String.format("患者(%s)无有效住院证, 无法关联陪护", patientCardNo));
            }
        } else if (inps.size() == 1) {
            // 强陪护，取住院证关联的陪护
            var inp = inps.get(0);
            fip = this.finIprPrepayInMapper.queryPrepayIn(inp.cardNo, inp.happenNo);
            if (fip == null) {
                throw new RuntimeException(String.format("患者(%s)住院记录(%s)未关联住院证, 无法判断关联数据", inp.cardNo, inp.patientNo));
            }
            fip.associateEntity.patient = inp;
        } else {
            throw new RuntimeException(String.format("患者(%s)存在多条住院记录, 无法判断关联数据", patientCardNo));
        }

        // 陪护人对象
        var helper = this.patientMapper.queryPatientInfo(helperCardNo);

        // 权限判断
        this.canRegister(fip, helper);

        // 插入VIP记录
        var vip = this.escortVipMapper.queryEscortVip(fip.cardNo, fip.happenNo);
        if (vip == null) {
            vip = new EscortVip();
            vip.patientCardNo = fip.cardNo;
            vip.happenNo = fip.happenNo;
            vip.helperCardNo = helperCardNo;
            vip.recDate = new Date();
            vip.remark = "首次登记陪护人";
            this.escortVipMapper.insertEscortVip(vip);
        }

        // 创建新陪护实体
        var escort = new EscortMainInfo();
        escort.escortNo = null;
        escort.patientCardNo = fip.cardNo;
        escort.happenNo = fip.happenNo;
        escort.helperCardNo = helperCardNo;
        escort.remark = "";

        // 插入陪护证主表
        this.escortMainInfoMapper.insertEscortMainInfo(escort);

        // 更新实时状态（传递事务）
        var curState = this.queryRealState(escort);
        var stateRec = this.selfService.updateEscortState(escort.escortNo, curState, emplCode, remark);

        // 更改状态实体
        escort.associateEntity.stateRecs = new ArrayList<>() {
            {
                add(stateRec);
            }
        };

        return escort;
    }

    @Transactional
    @Override
    public EscortStateRec updateEscortState(String escortNo, EscortStateEnum state, String emplCode,
            String remark) {
        // 查询陪护证
        var escort = this.escortMainInfoMapper.queryEscortMainInfo(escortNo);
        if (escort == null) {
            throw new RuntimeException(String.format("陪护证(%s)不存在", escortNo));
        }

        // 如果新状态被置为null，则表示要求自动更新
        if (state == null) {
            state = this.queryRealState(escort);
        }

        // 查询当前状态
        var curState = this.escortStateRecMapper.queryCurState(escortNo);
        if (curState != null) {
            if (curState.state == state) {
                return curState;
            } else if (curState.state == EscortStateEnum.注销) {
                throw new RuntimeException(String.format("陪护证(%s)已注销，无法再改变状态", escortNo));
            }
        }

        // 创建新状态
        var stateRec = new EscortStateRec();
        stateRec.escortNo = escortNo;
        stateRec.recNo = null;
        stateRec.state = state;
        stateRec.recEmplCode = emplCode;
        stateRec.recDate = new Date();
        stateRec.remark = remark;

        // 插入状态记录
        this.escortStateRecMapper.insertState(stateRec);

        return stateRec;
    }

    @Transactional
    @Override
    public EscortActionRec recordEscortAction(String escortNo, EscortActionEnum action, String remark) {
        // 查询陪护证
        var escort = this.escortMainInfoMapper.queryEscortMainInfo(escortNo);
        if (escort == null) {
            throw new RuntimeException(String.format("陪护证(%s)不存在", escortNo));
        }

        // 创建新状态
        var actionRec = new EscortActionRec();
        actionRec.escortNo = escortNo;
        actionRec.recNo = null;
        actionRec.action = action;
        actionRec.recDate = new Date();
        actionRec.remark = remark;

        // 插入状态记录
        this.escortActionRecMapper.insertAction(actionRec);

        return actionRec;
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
        // 查询陪护人关联的上下文
        var rs = this.escortMainInfoMapper.queryEscortMainInfos(null, null, helperCardNo, new ArrayList<>() {
            {
                add(EscortStateEnum.无核酸检测结果);
                add(EscortStateEnum.等待院内核酸检测结果);
                add(EscortStateEnum.等待院外核酸检测结果审核);
                add(EscortStateEnum.生效中);
            }
        });

        // 填充实体
        for (var rt : rs) {
            // 住院证
            rt.associateEntity.finIprPrepayIn = this.finIprPrepayInMapper.queryPrepayIn(rt.patientCardNo, rt.happenNo);
            if (rt.associateEntity.finIprPrepayIn != null) {
                // 若已入院，则存住院信息
                var inpatients = this.inpatientMapper.queryInpatientsWithPrepayIn(rt.patientCardNo, rt.happenNo);
                if (inpatients != null && inpatients.size() == 1) {
                    var ipt = inpatients.get(0);
                    ipt.associateEntity.stayedDept = this.departmentMapper.queryDepartment(ipt.stayedDeptCode);
                    if (ipt.bedNo != null) {
                        ipt.associateEntity.bed = this.comBedInfoMapper.queryBedInfo(ipt.bedNo);
                    }
                    rt.associateEntity.finIprPrepayIn.associateEntity.patient = ipt;
                } else {
                    rt.associateEntity.finIprPrepayIn.associateEntity.patient = this.patientMapper
                            .queryPatientInfo(rt.patientCardNo);
                }
                // 科室信息
                if (rt.associateEntity.finIprPrepayIn.preDeptCode != null) {
                    rt.associateEntity.finIprPrepayIn.associateEntity.preDept = this.departmentMapper
                            .queryDepartment(rt.associateEntity.finIprPrepayIn.preDeptCode);
                }
                // 床位信息
                if (rt.associateEntity.finIprPrepayIn.bedNo != null) {
                    rt.associateEntity.finIprPrepayIn.associateEntity.bedInfo = this.comBedInfoMapper
                            .queryBedInfo(rt.associateEntity.finIprPrepayIn.bedNo);
                }
                // vip
                rt.associateEntity.finIprPrepayIn.associateEntity.escortVip = this.escortVipMapper
                        .queryEscortVip(rt.patientCardNo, rt.happenNo);
            }
            rt.associateEntity.stateRecs = this.escortStateRecMapper.queryStates(rt.escortNo);
            rt.associateEntity.actionRecs = this.escortActionRecMapper.queryActions(rt.escortNo);
        }

        return rs;
    }

    @Override
    public List<EscortMainInfo> queryHelperInfos(String patientCardNo) {
        // 查询陪护人关联的上下文
        var rs = this.escortMainInfoMapper.queryEscortMainInfos(patientCardNo, null, null, new ArrayList<>() {
            {
                add(EscortStateEnum.无核酸检测结果);
                add(EscortStateEnum.等待院内核酸检测结果);
                add(EscortStateEnum.等待院外核酸检测结果审核);
                add(EscortStateEnum.生效中);
            }
        });

        // 填充实体
        for (var rt : rs) {
            // 住院证
            rt.associateEntity.finIprPrepayIn = this.finIprPrepayInMapper.queryPrepayIn(rt.patientCardNo, rt.happenNo);
            // 助手实体
            rt.associateEntity.helper = this.patientMapper.queryPatientInfo(rt.helperCardNo);
            if (rt.associateEntity.finIprPrepayIn != null) {
                // vip
                rt.associateEntity.finIprPrepayIn.associateEntity.escortVip = this.escortVipMapper
                        .queryEscortVip(rt.patientCardNo, rt.happenNo);
            }
            rt.associateEntity.stateRecs = this.escortStateRecMapper.queryStates(rt.escortNo);
            rt.associateEntity.actionRecs = this.escortActionRecMapper.queryActions(rt.escortNo);
        }

        return rs;
    }

    @Override
    public List<String> queryUncanceledEscortNos() {
        // 查询所有未注销的陪护证
        var escorts = this.escortMainInfoMapper.queryEscortMainInfos(null, null, null, new ArrayList<>() {
            {
                add(EscortStateEnum.无核酸检测结果);
                add(EscortStateEnum.等待院内核酸检测结果);
                add(EscortStateEnum.等待院外核酸检测结果审核);
                add(EscortStateEnum.生效中);
            }
        });

        return escorts.stream().map((x) -> x.escortNo).toList();
    }
}
