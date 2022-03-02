package com.kaos.his.controller.impl.inpatient.escort;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.kaos.his.cache.impl.common.ComPatientInfoCache;
import com.kaos.his.cache.impl.common.DawnOrgDeptCache;
import com.kaos.his.cache.impl.inpatient.ComBedInfoCache;
import com.kaos.his.controller.MediaType;
import com.kaos.his.controller.inf.inpatient.escort.MainController;
import com.kaos.his.enums.impl.inpatient.escort.EscortActionEnum;
import com.kaos.his.enums.impl.inpatient.escort.EscortStateEnum;
import com.kaos.his.service.inf.inpatient.escort.MainService;
import com.kaos.his.util.helper.DateHelper;
import com.kaos.his.util.helper.ListHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping({ "/ms/inpatient/escort/main", "/ms/inpatient/escort" })
public class MainControllerImpl implements MainController {
    /**
     * 日志模块
     */
    Logger logger = Logger.getLogger(MainControllerImpl.class);

    /**
     * 业务模块
     */
    @Autowired
    MainService escortMainService;

    /**
     * 列表操作助手
     */
    @Autowired
    ListHelper listHelper;

    /**
     * 时间操作助手
     */
    @Autowired
    DateHelper dateHelper;

    /**
     * 患者基本信息cache
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 床位信息cache
     */
    @Autowired
    ComBedInfoCache bedInfoCache;

    /**
     * 科室信息cache
     */
    @Autowired
    DawnOrgDeptCache deptCache;

    @Override
    @RequestMapping(value = "register", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String register(@NotNull(message = "患者卡号不能为空") String patientCardNo,
            @NotNull(message = "陪护人卡号不能为空") String helperCardNo,
            @NotNull(message = "操作员编码不能为空") String emplCode,
            String remark) {
        // 入参日志
        this.logger.info(String.format("登记陪护人<patientCardNo = %s, helperCardNo = %s, emplCode = %s>", patientCardNo,
                helperCardNo, emplCode));

        // 加患者锁，防止同时对同一个患者添加陪护
        synchronized (Lock.patientLock.mapToLock(patientCardNo)) {
            // 加陪护人锁，防止同时对同一个陪护人添加陪护
            synchronized (Lock.helperLock.mapToLock(helperCardNo)) {
                // 调用业务
                var escort = this.escortMainService.registerEscort(patientCardNo, helperCardNo, emplCode, remark);
                if (escort != null) {
                    return escort.escortNo;
                }
            }
        }

        return null;
    }

    @Override
    public void updateState(@NotNull(message = "陪护证号不能为空") String escortNo,
            @NotNull(message = "新状态不能为空") EscortStateEnum state,
            @NotNull(message = "操作员编码不能为空") String emplCode) {
        // 入参日志
        this.logger.info(String.format("修改陪护证状态<escortNo = %s, state = %s, emplCode = %s>", escortNo,
                state.getDescription(), emplCode));

        // 加状态操作锁，防止同时操作同一个陪护证
        synchronized (Lock.stateLock.mapToLock(escortNo)) {
            // 调用业务
            this.escortMainService.updateEscortState(escortNo, state, emplCode, "收到客户端请求");
        }
    }

    @Override
    public void recordAction(@NotNull(message = "陪护证号不能为空") String escortNo,
            @NotNull(message = "记录的动作不能为空") EscortActionEnum action) {
        // 入参日志
        this.logger.info(String.format("记录陪护证行为<escortNo = %s, action = %s>", escortNo, action.getDescription()));

        // 加状态操作锁，防止同时操作同一个陪护证
        synchronized (Lock.actionLock.mapToLock(escortNo)) {
            // 调用业务
            this.escortMainService.recordEscortAction(escortNo, action, "收到客户端请求");
        }
    }

    @Override
    public QueryStateInfoRsp queryStateInfo(@NotNull(message = "陪护证号不能为空") String escortNo) {
        // 入参日志
        this.logger.info(String.format("查询陪护证状态<escortNo = %s>", escortNo));

        // 调用业务
        var escortInfo = this.escortMainService.queryEscortStateInfo(escortNo);
        if (escortInfo == null) {
            return null;
        }

        // 构造响应体
        var rsp = new QueryStateInfoRsp();
        rsp.patientCardNo = escortInfo.patientCardNo;
        rsp.helperCardNo = escortInfo.helperCardNo;
        if (escortInfo.associateEntity.stateRecs != null) {
            rsp.regDate = this.listHelper.getFirst(escortInfo.associateEntity.stateRecs).recDate;
            rsp.state = this.listHelper.getLast(escortInfo.associateEntity.stateRecs).state.getValue();
        }

        return rsp;
    }

    @Override
    public List<QueryPatientsRsp> queryPatients(@NotNull(message = "陪护人卡号不能为空") String helperCardNo) {
        // 入参日志
        this.logger.info(String.format("查询陪护患者信息<helperCardNo = %s>", helperCardNo));

        // 调用业务
        var escortInfos = this.escortMainService.queryPatientInfos(helperCardNo);
        if (escortInfos == null) {
            return null;
        }

        // 构造响应body
        List<QueryPatientsRsp> rsps = Lists.newArrayList();
        for (var escortInfo : escortInfos) {
            // 构造列表元素
            QueryPatientsRsp rsp = new QueryPatientsRsp();
            rsp.cardNo = escortInfo.patientCardNo;
            var patient = this.patientInfoCache.getValue(rsp.cardNo);
            if (patient != null) {
                rsp.name = patient.name;
                rsp.sex = patient.sex;
                rsp.age = this.dateHelper.getAge(patient.birthday).toString();
            }
            if (escortInfo.associateEntity.prepayIn != null) {
                // 若存在住院证，加载住院信息
                var prepayIn = escortInfo.associateEntity.prepayIn;
                if (prepayIn.associateEntity.inMainInfo != null) {
                    // 若已入院，加载在院信息
                    var inMainInfo = prepayIn.associateEntity.inMainInfo;
                    rsp.deptName = inMainInfo.deptName;
                    if (this.bedInfoCache.getValue(inMainInfo.bedNo) != null) {
                        rsp.bedNo = this.bedInfoCache.getValue(inMainInfo.bedNo).getBriefBedNo();
                    }
                    rsp.patientNo = inMainInfo.patientNo;
                } else {
                    // 若未入院，加载住院证信息
                    if (this.deptCache.getValue(prepayIn.preDeptCode) != null) {
                        rsp.deptName = this.deptCache.getValue(prepayIn.preDeptCode).deptName;
                    }
                    if (this.bedInfoCache.getValue(prepayIn.bedNo) != null) {
                        rsp.bedNo = this.bedInfoCache.getValue(prepayIn.bedNo).getBriefBedNo();
                    }
                }
                if (prepayIn.associateEntity.escortVip != null) {
                    if (prepayIn.associateEntity.escortVip.helperCardNo.equals(escortInfo.helperCardNo)) {
                        rsp.freeFlag = "1";
                    } else {
                        rsp.freeFlag = "0";
                    }
                }
            }
            rsp.escortNo = escortInfo.escortNo;
            if (escortInfo.associateEntity.stateRecs != null) {
                rsp.states = escortInfo.associateEntity.stateRecs.stream().map(x -> {
                    var state = new EscortStateRec();
                    state.recNo = x.recNo;
                    state.state = x.state;
                    state.recEmplCode = x.recEmplCode;
                    state.recDate = x.recDate;
                    return state;
                }).toList();
            }
            if (escortInfo.associateEntity.actionRecs != null) {
                rsp.actions = escortInfo.associateEntity.actionRecs.stream().map(x -> {
                    var action = new EscortActionRec();
                    action.recNo = x.recNo;
                    action.action = x.action;
                    action.recDate = x.recDate;
                    return action;
                }).toList();
            }

            // 加入结果集
            rsps.add(rsp);
        }

        return rsps;
    }

    @Override
    public List<QueryHelpersRsp> queryHelpers(@NotNull(message = "患者卡号不能为空") String patientCardNo) {
        // 入参日志
        this.logger.info(String.format("查询患者陪护人信息<patientCardNo = %s>", patientCardNo));

        // 调用业务
        var escortInfos = this.escortMainService.queryHelperInfos(patientCardNo);
        if (escortInfos == null) {
            return null;
        }

        // 构造响应body
        List<QueryHelpersRsp> rsps = Lists.newArrayList();
        for (var escortInfo : escortInfos) {
            // 构造列表元素
            QueryHelpersRsp rsp = new QueryHelpersRsp();
            rsp.cardNo = escortInfo.patientCardNo;
            var patient = this.patientInfoCache.getValue(rsp.cardNo);
            if (patient != null) {
                rsp.name = patient.name;
                rsp.sex = patient.sex;
                rsp.age = this.dateHelper.getAge(patient.birthday).toString();
            }
            if (escortInfo.associateEntity.prepayIn != null) {
                // 若存在住院证，加载住院信息
                var prepayIn = escortInfo.associateEntity.prepayIn;
                if (prepayIn.associateEntity.escortVip != null) {
                    if (prepayIn.associateEntity.escortVip.helperCardNo.equals(escortInfo.helperCardNo)) {
                        rsp.freeFlag = "1";
                    } else {
                        rsp.freeFlag = "0";
                    }
                }
            }
            rsp.escortNo = escortInfo.escortNo;
            if (escortInfo.associateEntity.stateRecs != null) {
                rsp.states = escortInfo.associateEntity.stateRecs.stream().map(x -> {
                    var state = new EscortStateRec();
                    state.recNo = x.recNo;
                    state.state = x.state;
                    state.recEmplCode = x.recEmplCode;
                    state.recDate = x.recDate;
                    return state;
                }).toList();
            }
            if (escortInfo.associateEntity.actionRecs != null) {
                rsp.actions = escortInfo.associateEntity.actionRecs.stream().map(x -> {
                    var action = new EscortActionRec();
                    action.recNo = x.recNo;
                    action.action = x.action;
                    action.recDate = x.recDate;
                    return action;
                }).toList();
            }

            // 加入结果集
            rsps.add(rsp);
        }

        return rsps;
    }
}
