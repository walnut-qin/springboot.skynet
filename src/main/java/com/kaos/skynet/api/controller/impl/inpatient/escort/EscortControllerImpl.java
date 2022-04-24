package com.kaos.skynet.api.controller.impl.inpatient.escort;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.cache.impl.common.ComPatientInfoCache;
import com.kaos.skynet.api.cache.impl.common.DawnOrgDeptCache;
import com.kaos.skynet.api.cache.impl.inpatient.ComBedInfoCache;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.inpatient.escort.EscortController;
import com.kaos.skynet.api.service.inf.inpatient.escort.EscortService;
import com.kaos.skynet.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.enums.impl.inpatient.escort.EscortActionEnum;
import com.kaos.skynet.enums.impl.inpatient.escort.EscortStateEnum;
import com.kaos.skynet.util.DateHelpers;
import com.kaos.skynet.util.Gsons;
import com.kaos.skynet.util.ListHelpers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/inpatient/escort")
public class EscortControllerImpl implements EscortController {
    /**
     * 日志模块
     */
    Logger logger = Logger.getLogger(EscortControllerImpl.class);

    /**
     * Gson工具
     */
    Gson gson = Gsons.newGson();

    /**
     * 业务模块
     */
    @Autowired
    EscortService escortMainService;

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

    /**
     * 住院实体cache
     */
    @Autowired
    Cache<String, FinIprInMainInfo> inMainInfoCache;

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
        synchronized (Locks.patientLock.mapToLock(patientCardNo)) {
            // 加陪护人锁，防止同时对同一个陪护人添加陪护
            synchronized (Locks.helperLock.mapToLock(helperCardNo)) {
                // 调用业务
                var escort = this.escortMainService.registerEscort(patientCardNo, helperCardNo, emplCode, remark,
                        false);
                if (escort != null) {
                    return escort.escortNo;
                }
            }
        }

        return null;
    }

    @Override
    @RequestMapping(value = "register", method = RequestMethod.POST, produces = MediaType.TEXT)
    public String register(@RequestBody @Valid RegisterReq req) {
        // 记录日志
        this.logger.info(String.format("登记陪护证, 入参%s", this.gson.toJson(req)));

        // 优先视作用住院号登记
        var inPat = this.inMainInfoCache.getValue(req.patientIdx);
        var patientLock = Locks.patientLock.mapToLock(inPat == null ? req.patientIdx : inPat.cardNo);

        // 加患者锁，防止同时对同一个患者添加陪护
        synchronized (patientLock) {
            // 加陪护人锁，防止同时对同一个陪护人添加陪护
            synchronized (Locks.helperLock.mapToLock(req.helperCardNo)) {
                // 调用业务
                var escort = this.escortMainService.registerEscort(req.patientIdx, req.helperCardNo, req.emplCode,
                        req.remark, Optional.fromNullable(req.getRegbyWindow()).or(false));
                if (escort != null) {
                    return escort.escortNo;
                }
            }
        }

        return null;
    }

    @Override
    @RequestMapping(value = "updateState", method = RequestMethod.GET, produces = MediaType.TEXT)
    public void updateState(@NotNull(message = "陪护证号不能为空") String escortNo,
            EscortStateEnum state,
            @NotNull(message = "操作员编码不能为空") String emplCode) {
        // 入参日志
        this.logger.info(String.format("修改陪护证状态<escortNo = %s, state = %s, emplCode = %s>", escortNo,
                state == null ? "null" : state.getDescription(), emplCode));

        // 加状态操作锁，防止同时操作同一个陪护证
        synchronized (Locks.stateLock.mapToLock(escortNo)) {
            // 调用业务
            this.escortMainService.updateEscortState(escortNo, state, emplCode, "收到客户端请求");
        }
    }

    @Override
    @RequestMapping(value = "recordAction", method = RequestMethod.GET, produces = MediaType.TEXT)
    public void recordAction(@NotNull(message = "陪护证号不能为空") String escortNo,
            @NotNull(message = "记录的动作不能为空") EscortActionEnum action) {
        // 入参日志
        this.logger.info(String.format("记录陪护证行为<escortNo = %s, action = %s>", escortNo, action.getDescription()));

        // 加状态操作锁，防止同时操作同一个陪护证
        synchronized (Locks.actionLock.mapToLock(escortNo)) {
            // 调用业务
            this.escortMainService.recordEscortAction(escortNo, action, "收到客户端请求");
        }
    }

    @Override
    @RequestMapping(value = "queryStateInfo", method = RequestMethod.GET, produces = MediaType.JSON)
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
            rsp.regDate = ListHelpers.getFirst(escortInfo.associateEntity.stateRecs).recDate;
            rsp.state = ListHelpers.getLast(escortInfo.associateEntity.stateRecs).state.getValue();
        }

        return rsp;
    }

    @Override
    @RequestMapping(value = "queryPatientInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<QueryPatientInfoRsp> queryPatientInfo(@NotNull(message = "陪护人卡号不能为空") String helperCardNo) {
        // 入参日志
        this.logger.info(String.format("查询陪护患者信息<helperCardNo = %s>", helperCardNo));

        // 调用业务
        var escortInfos = this.escortMainService.queryPatientInfos(helperCardNo);
        if (escortInfos == null) {
            return null;
        }

        // 构造响应body
        List<QueryPatientInfoRsp> rsps = Lists.newArrayList();
        for (var escortInfo : escortInfos) {
            // 构造列表元素
            QueryPatientInfoRsp rsp = new QueryPatientInfoRsp();
            rsp.cardNo = escortInfo.patientCardNo;
            var patient = this.patientInfoCache.getValue(rsp.cardNo);
            if (patient != null) {
                rsp.name = patient.name;
                rsp.sex = patient.sex;
                rsp.age = DateHelpers.getAge(patient.birthday).toString();
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
    @RequestMapping(value = "queryHelperInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<QueryHelperInfoRsp> queryHelperInfo(@NotNull(message = "患者卡号不能为空") String patientCardNo) {
        // 入参日志
        this.logger.info(String.format("查询患者陪护人信息<patientCardNo = %s>", patientCardNo));

        // 调用业务
        var escortInfos = this.escortMainService.queryHelperInfos(patientCardNo);
        if (escortInfos == null) {
            return null;
        }

        // 构造响应body
        List<QueryHelperInfoRsp> rsps = Lists.newArrayList();
        for (var escortInfo : escortInfos) {
            // 构造列表元素
            QueryHelperInfoRsp rsp = new QueryHelperInfoRsp();
            rsp.cardNo = escortInfo.helperCardNo;
            var patient = this.patientInfoCache.getValue(rsp.cardNo);
            if (patient != null) {
                rsp.name = patient.name;
                rsp.sex = patient.sex;
                rsp.age = DateHelpers.getAge(patient.birthday).toString();
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
