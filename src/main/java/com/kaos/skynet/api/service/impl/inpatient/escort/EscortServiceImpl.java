package com.kaos.skynet.api.service.impl.inpatient.escort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.data.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.mapper.outpatient.fee.FinOpbFeeDetailMapper;
import com.kaos.skynet.api.data.mapper.pipe.lis.LisResultNewMapper;
import com.kaos.skynet.api.data.mapper.pipe.lis.LisResultNewMapper.Key;
import com.kaos.skynet.api.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.entity.inpatient.FinIprPrepayIn;
import com.kaos.skynet.api.entity.inpatient.escort.EscortActionRec;
import com.kaos.skynet.api.entity.inpatient.escort.EscortAnnexChk;
import com.kaos.skynet.api.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.entity.inpatient.escort.EscortVip;
import com.kaos.skynet.api.enums.inpatient.FinIprPrepayInStateEnum;
import com.kaos.skynet.api.enums.inpatient.InStateEnum;
import com.kaos.skynet.api.enums.inpatient.escort.EscortActionEnum;
import com.kaos.skynet.api.enums.inpatient.escort.EscortStateEnum;
import com.kaos.skynet.api.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortActionRecMapper;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortAnnexChkMapper;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortAnnexInfoMapper;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortVipMapper;
import com.kaos.skynet.api.mapper.inpatient.order.MetOrdiOrderMapper;
import com.kaos.skynet.api.service.impl.inpatient.fee.report.ReportServiceImpl;
import com.kaos.skynet.api.service.inf.inpatient.escort.EscortService;

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
    Logger logger = Logger.getLogger(ReportServiceImpl.class.getName());

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
     * 住院实体接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 基本信息缓存
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 住院主表缓存
     */
    @Autowired
    Cache<String, FinIprInMainInfo> inMainInfoCache;

    /**
     * 判断陪护人的信息合法性
     * 
     * @param helperCardNo 陪护人卡号
     * @return 若可以判断状态，则返回状态值，否则返回null
     */
    private EscortStateEnum queryRealState_helper(String helperCardNo) {
        // 获取陪护人实体
        var patientInfo = this.patientInfoCache.get(helperCardNo);
        if (patientInfo == null) {
            // 无法查询到陪护人实体，无法判断状态
            return null;
        }

        // 健康码判断
        if (patientInfo.getHealthCode() != null) {
            switch (patientInfo.getHealthCode()) {
                case 绿码:
                    break;

                case 黄码:
                case 红码:
                    return EscortStateEnum.无核酸检测结果;

                default:
                    break;
            }
        }

        // 行程码判断
        if (patientInfo.getTravelCode() != null) {
            switch (patientInfo.getTravelCode()) {
                case 正常:
                    break;

                case 带星号:
                case 黄码:
                case 红码:
                    return EscortStateEnum.无核酸检测结果;

                default:
                    break;
            }
        }

        // 高风险地区标识判断
        if (Optional.fromNullable(patientInfo.getHighRiskFlag()).or(false)) {
            return EscortStateEnum.无核酸检测结果;
        }

        return null;
    }

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
        EscortStateEnum orgState = null;
        if (ntt.stateRecs == null) {
            ntt.stateRecs = this.escortStateRecMapper.queryStates(context.escortNo);
        }
        if (ntt.stateRecs != null && !ntt.stateRecs.isEmpty()) {
            orgState = ntt.stateRecs.get(ntt.stateRecs.size() - 1).state;
            if (orgState == EscortStateEnum.注销) {
                return EscortStateEnum.注销;
            }
        }

        // 获取关联住院证
        if (ntt.prepayIn == null) {
            ntt.prepayIn = this.finIprPrepayInMapper.queryPrepayIn(context.patientCardNo, context.happenNo);
            if (ntt.prepayIn == null) {
                throw new RuntimeException(String.format("陪护证(%s)关联的住院证不存在，无法判断状态", context.escortNo));
            }
        }

        // 住院证有效？
        var fip = ntt.prepayIn;
        switch (fip.state) {
            case 作废:
                return EscortStateEnum.注销;

            default:
                break;
        }

        // 获取住院实体/患者实体
        var inMainInfos = this.inMainInfoMapper.queryInpatients(context.patientCardNo, context.happenNo, null, null);
        if (inMainInfos.size() >= 2) {
            // 过滤出院记录
            inMainInfos = Collections2.filter(inMainInfos, new Predicate<FinIprInMainInfo>() {
                @Override
                public boolean apply(@Nullable FinIprInMainInfo input) {
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
        switch (inMainInfos.size()) {
            // 弱陪护
            case 0:
                var lastFip = this.finIprPrepayInMapper.queryLastPrepayIn(fip.cardNo, null);
                if (!lastFip.happenNo.equals(fip.happenNo)) {
                    return EscortStateEnum.注销;
                }
                break;

            // 强陪护
            case 1:
                var inMainInfo = inMainInfos.get(0);
                switch (inMainInfo.inState) {
                    case 出院结算:
                    case 无费退院:
                        if (new Date().getTime() - inMainInfo.outDate.getTime() >= 6 * 60 * 60 * 1000) {
                            return EscortStateEnum.注销;
                        }
                        break;

                    case 出院登记:
                        if (new Date().getTime() - inMainInfo.outDate.getTime() >= 12 * 60 * 60 * 1000) {
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

        // 增加对陪护人健康码的判断
        var tmpResult = this.queryRealState_helper(context.helperCardNo);
        if (tmpResult != null) {
            return tmpResult;
        }

        // 若当前无效，则锚定2天前，否则锚定14天前
        var ptr = LocalDateTime.now();
        if (orgState != null && orgState == EscortStateEnum.生效中) {
            ptr = ptr.plusDays(-14);
        } else {
            ptr = ptr.plusDays(-2);
        }
        final LocalDateTime beginDate = ptr;

        // 查询7日内本院核酸记录
        var lisRs = this.lisResultNewMapper.queryInspectResults(new Key() {
            {
                setPatientId(context.helperCardNo);
                setItemCodes(Lists.newArrayList("SARS-CoV-2-RNA"));
                setBeginDate(beginDate);
                setEndDate(null);
            }
        });
        if (lisRs != null && !lisRs.isEmpty()) {
            var lastLisRt = lisRs.get(0);
            if (lastLisRt.getResult().equals("阴性(-)")) {
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
                if (lastRecord == null || lastRecord.inspectDate.isBefore(curRec.inspectDate)) {
                    lastRecord = curRec;
                }
            }
            // 如果最近一次结果为阴性，则生效
            if (lastRecord.inspectDate.isAfter(beginDate) && lastRecord.negativeFlag) {
                return EscortStateEnum.生效中;
            }
        }

        // 查询7日内划价记录
        var fees = finOpbFeeDetailMapper.queryFeeDetails(new FinOpbFeeDetailMapper.Key() {
            {
                setCardNo(context.helperCardNo);
                setItemCode("F00000068231");
                setOperBeginDate(beginDate);
            }
        });
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
     * @param fip          住院证
     * @param helperCardNo 陪护人卡号
     * @param regByWindow  是否为窗口操作，若是，则逃逸掉12小时反复横跳判断
     * @throws RuntimeException
     */
    private void canRegister(FinIprPrepayIn fip, String helperCardNo, Boolean regByWindow) throws RuntimeException {
        // 入参判断
        if (fip == null) {
            throw new RuntimeException("住院证不存在");
        }

        // 判断0：自陪护
        if (fip.cardNo.equals(helperCardNo)) {
            throw new RuntimeException("不可以给自己陪护");
        }

        // 判断1：已陪护 || 刚注销
        var lastEscort = this.escortMainInfoMapper.queryLastEscortMainInfo(fip.cardNo, null, helperCardNo, null);
        if (lastEscort != null) {
            var curState = this.escortStateRecMapper.queryCurState(lastEscort.escortNo);
            if (curState.state != EscortStateEnum.注销) {
                throw new RuntimeException("陪护关系已绑定，请勿重复绑定");
            } else if (!regByWindow && lastEscort.happenNo.equals(fip.happenNo)) {
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
                if (fip.associateEntity.inMainInfo != null) {
                    FinIprInMainInfo inPat = fip.associateEntity.inMainInfo;
                    var ords = this.metOrdiOrderMapper.queryInpatientOrders(inPat.inpatientNo, "5070672", null, null);
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
     * 根据患者索引定位唯一有效的住院证
     * 
     * @param patientIdx 住院证号或就诊卡号
     * @return
     */
    private FinIprPrepayIn queryUniqueValidPrepayIn(String patientIdx) {
        // 优先认为patientIdx是住院号，检索住院实体
        var inPat = this.inMainInfoCache.getValue(String.format("ZY01%s", patientIdx));
        if (inPat != null) {
            // 判断状态
            switch (inPat.inState) {
                case 住院登记:
                case 病房接诊:
                    break;

                default:
                    throw new RuntimeException("此住院号非在院状态");
            }
            // 核查住院证是否关联唯一的住院实体
            var relaInPats = this.inMainInfoMapper.queryInpatients(inPat.cardNo, null, null,
                    Lists.newArrayList(InStateEnum.住院登记, InStateEnum.病房接诊));
            if (relaInPats.size() != 1) {
                throw new RuntimeException("患者存在多条在院记录");
            }
            // 检索住院证
            var fip = this.finIprPrepayInMapper.queryPrepayIn(inPat.cardNo, inPat.happenNo);
            if (fip == null) {
                throw new RuntimeException("未检索到住院证");
            } else {
                fip.associateEntity.inMainInfo = inPat;
            }
            return fip;
        } else {
            // 尝试获取住院证
            var inMainInfos = this.inMainInfoMapper.queryInpatients(patientIdx, null, null,
                    Lists.newArrayList(InStateEnum.住院登记, InStateEnum.病房接诊));
            switch (Optional.fromNullable(inMainInfos).or(Lists.newArrayList()).size()) {
                case 0: {
                    // 弱陪护，直接取最后一张住院证
                    var fip = this.finIprPrepayInMapper.queryLastPrepayIn(patientIdx, null);
                    if (fip == null || fip.state == FinIprPrepayInStateEnum.作废) {
                        throw new RuntimeException(String.format("未检索到有效住院证", patientIdx));
                    }
                    return fip;
                }

                case 1: {
                    // 强陪护，取住院证关联的陪护
                    var inMainInfo = inMainInfos.get(0);
                    var fip = this.finIprPrepayInMapper.queryPrepayIn(inMainInfo.cardNo, inMainInfo.happenNo);
                    if (fip == null) {
                        throw new RuntimeException(String.format("未检索到住院证", inMainInfo.cardNo, inMainInfo.patientNo));
                    } else {
                        fip.associateEntity.inMainInfo = inMainInfo;
                    }
                    return fip;
                }

                default:
                    throw new RuntimeException(String.format("检索到多条在院记录", patientIdx));
            }
        }
    }

    @Transactional
    @Override
    public EscortMainInfo registerEscort(String patientIdx, String helperCardNo, String emplCode, String remark,
            Boolean regByWindow) {
        // 判定唯一有效的住院证
        var prepayIn = this.queryUniqueValidPrepayIn(patientIdx);

        // 权限判断
        this.canRegister(prepayIn, helperCardNo, regByWindow);

        // 插入VIP记录
        var vip = this.escortVipMapper.queryEscortVip(prepayIn.cardNo, prepayIn.happenNo);
        if (vip == null) {
            vip = new EscortVip();
            vip.patientCardNo = prepayIn.cardNo;
            vip.happenNo = prepayIn.happenNo;
            vip.helperCardNo = helperCardNo;
            vip.recDate = new Date();
            vip.remark = "首次登记陪护人";
            this.escortVipMapper.insertEscortVip(vip);
        }

        // 创建新陪护实体
        var escort = new EscortMainInfo();
        escort.escortNo = null;
        escort.patientCardNo = prepayIn.cardNo;
        escort.happenNo = prepayIn.happenNo;
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

    @Override
    @Transactional(timeout = 10)
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
            rt.associateEntity.prepayIn = this.finIprPrepayInMapper.queryPrepayIn(rt.patientCardNo, rt.happenNo);
            if (rt.associateEntity.prepayIn != null) {
                // vip
                rt.associateEntity.prepayIn.associateEntity.escortVip = this.escortVipMapper
                        .queryEscortVip(rt.patientCardNo, rt.happenNo);
                // 基本信息
                rt.associateEntity.prepayIn.associateEntity.patientInfo = this.patientInfoCache.get(rt.patientCardNo);
                // 住院实体
                var inMainInfos = this.inMainInfoMapper.queryInpatients(rt.patientCardNo, rt.happenNo, null,
                        Lists.newArrayList(InStateEnum.住院登记, InStateEnum.病房接诊));
                switch (Optional.fromNullable(inMainInfos).or(Lists.newArrayList()).size()) {
                    case 1:
                        rt.associateEntity.prepayIn.associateEntity.inMainInfo = inMainInfos.get(0);
                        break;
                    default:
                        break;
                }
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
            rt.associateEntity.prepayIn = this.finIprPrepayInMapper.queryPrepayIn(rt.patientCardNo, rt.happenNo);
            if (rt.associateEntity.prepayIn != null) {
                // vip
                rt.associateEntity.prepayIn.associateEntity.escortVip = this.escortVipMapper
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

        return escorts.stream().map(x -> x.escortNo).toList();
    }
}
