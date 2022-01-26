package com.kaos.his.controller.inpatient.escort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kaos.helper.type.TypeHelper;
import com.kaos.helper.type.impl.TypeHelperImpl;
import com.kaos.his.controller.inpatient.escort.entity.EscortActionRec;
import com.kaos.his.controller.inpatient.escort.entity.EscortStateRec;
import com.kaos.his.controller.inpatient.escort.entity.QueryAnnexInDeptRspBody;
import com.kaos.his.controller.inpatient.escort.entity.QueryHelperInfoRspBody;
import com.kaos.his.controller.inpatient.escort.entity.QueryPatientInfoRspBody;
import com.kaos.his.controller.inpatient.escort.entity.QueryStateInfoRspBody;
import com.kaos.his.entity.inpatient.Inpatient;
import com.kaos.his.enums.EscortActionEnum;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.service.inpatient.EscortService;
import com.kaos.util.GsonHelper;
import com.kaos.util.LockHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/inpatient/escort")
public class EscortController {
    /**
     * 接口：日志服务
     */
    Logger logger = Logger.getLogger(EscortController.class.getName());

    /**
     * 基本类型助手
     */
    TypeHelper typeHelper = new TypeHelperImpl();

    /**
     * 20个陪护证状态锁
     */
    public static final List<Object> stateLocks = new ArrayList<>() {
        {
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
        }
    };

    /**
     * 10个陪护证动作锁
     */
    public static final List<Object> actionLocks = new ArrayList<>() {
        {
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
        }
    };

    /**
     * 10个患者锁
     */
    public static final List<Object> patientLocks = new ArrayList<>() {
        {
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
        }
    };

    /**
     * 10个陪护锁
     */
    public static final List<Object> helperLocks = new ArrayList<>() {
        {
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
        }
    };

    /**
     * 陪护证服务
     */
    @Autowired
    EscortService escortService;

    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String register(@NotBlank(message = "患者卡号不能为空") String patientCardNo,
            @NotBlank(message = "陪护人卡号不能为空") String helperCardNo,
            @NotBlank(message = "操作员编码不能为空") String emplCode,
            @NotNull(message = "备注不能为空") String remark) {
        // 记录日志
        this.logger.info(String.format("登记陪护证(patientCardNo = %s, helperCardNo = %s)", patientCardNo, helperCardNo));

        try {
            synchronized (LockHelper.mapToLock(patientCardNo, patientLocks)) {
                this.logger.info("加锁(patientLock)");
                try {
                    synchronized (LockHelper.mapToLock(helperCardNo, helperLocks)) {
                        this.logger.info("加锁(helperLock)");

                        // 执行服务
                        var rt = this.escortService.registerEscort(patientCardNo, helperCardNo, emplCode, remark);
                        this.logger.info("业务执行成功");

                        return rt.escortNo;
                    }
                } finally {
                    this.logger.info("解锁(helperLock)");
                }
            }
        } finally {
            this.logger.info("解锁(patientLock)");
        }
    }

    @ResponseBody
    @RequestMapping(value = "updateState", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void updateState(@NotBlank(message = "陪护证号不能为空") String escortNo,
            @Nullable EscortStateEnum state,
            @NotBlank(message = "操作员编码不能为空") String emplCode) {
        // 记录日志
        this.logger.info(String.format("修改陪护证状态(escortNo = %s, state = %s, emplCode = %s)", escortNo,
                state, emplCode));

        try {
            synchronized (LockHelper.mapToLock(escortNo, stateLocks)) {
                this.logger.info("加锁(stateLock)");
                // 执行业务
                this.escortService.updateEscortState(escortNo, state, emplCode, "收到客户端请求");
                this.logger.info("业务执行成功");
            }
        } finally {
            this.logger.info("解锁(stateLock)");
        }
    }

    @ResponseBody
    @RequestMapping(value = "recordAction", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void recordAction(@NotBlank(message = "陪护证号不能为空") String escortNo,
            @Nullable EscortActionEnum action) {
        // 记录日志
        this.logger.info(String.format("记录陪护证行为(escortNo = %s, action = %s)", escortNo, action));

        try {
            synchronized (LockHelper.mapToLock(escortNo, actionLocks)) {
                this.logger.info("加锁(actionLock)");
                // 执行业务
                this.escortService.recordEscortAction(escortNo, action, "收到客户端请求");
                this.logger.info("业务执行成功");
            }
        } finally {
            this.logger.info("解锁(actionLock)");
        }
    }

    @ResponseBody
    @RequestMapping(value = "queryStateInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String queryStateInfo(@NotBlank(message = "陪护证号不能为空") String escortNo) {
        // 记录日志
        this.logger.info(String.format("查询陪护证 %s 的状态", escortNo));

        // 调用服务
        var srvRt = this.escortService.queryEscortStateInfo(escortNo);

        // 构造响应
        var rspBody = new QueryStateInfoRspBody();
        rspBody.patientCardNo = srvRt.patientCardNo;
        rspBody.escortCardNo = srvRt.helperCardNo;
        if (srvRt.associateEntity.stateRecs != null) {
            rspBody.regDate = this.typeHelper.getFirst(srvRt.associateEntity.stateRecs).recDate;
            rspBody.state = this.typeHelper.getLast(srvRt.associateEntity.stateRecs).state.getValue();
        }

        return GsonHelper.ToJson(rspBody);
    }

    @ResponseBody
    @RequestMapping(value = "queryPatientInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String queryPatientInfo(@NotBlank(message = "陪护人卡号不能为空") String helperCardNo) {
        // 记录日志
        this.logger.info(String.format("查询陪护的患者信息 %s 的状态", helperCardNo));

        // 调用服务
        var rspBody = new ArrayList<QueryPatientInfoRspBody>();
        for (var rt : this.escortService.queryPatientInfos(helperCardNo)) {
            // 创建元素
            var rspItem = new QueryPatientInfoRspBody();
            // 就诊卡号
            rspItem.cardNo = rt.patientCardNo;
            if (rt.associateEntity.finIprPrepayIn != null) {
                var fip = rt.associateEntity.finIprPrepayIn;
                // 姓名
                rspItem.name = fip.associateEntity.patient.name;
                // 性别
                rspItem.sex = fip.associateEntity.patient.sex;
                if (fip.associateEntity.patient != null) {
                    var patient = fip.associateEntity.patient;
                    // 年龄
                    rspItem.age = this.typeHelper.getAge(patient.birthday).toString();
                    if (patient instanceof Inpatient) {
                        var inpatient = (Inpatient) patient;
                        // 科室
                        if (inpatient.associateEntity.stayedDept == null) {
                            rspItem.deptName = inpatient.stayedDeptCode;
                        } else {
                            rspItem.deptName = inpatient.associateEntity.stayedDept.deptName;
                        }
                        // 床号
                        if (inpatient.associateEntity.bed == null) {
                            rspItem.bedNo = inpatient.bedNo;
                        } else {
                            rspItem.bedNo = inpatient.associateEntity.bed.getBriefBedNo();
                        }
                        // 住院号
                        rspItem.patientNo = inpatient.patientNo;
                    } else {
                        // 科室
                        if (fip.associateEntity.preDept == null) {
                            rspItem.deptName = fip.preDeptCode;
                        } else {
                            rspItem.deptName = fip.associateEntity.preDept.deptName;
                        }
                        // 床号
                        if (fip.associateEntity.bedInfo == null) {
                            rspItem.bedNo = fip.bedNo;
                        } else {
                            rspItem.bedNo = fip.associateEntity.bedInfo.getBriefBedNo();
                        }
                    }
                }
                if (fip.associateEntity.escortVip != null) {
                    var escortVip = fip.associateEntity.escortVip;
                    // 免费标识
                    rspItem.freeFlag = escortVip.helperCardNo.equals(rt.helperCardNo) ? "1" : "0";
                }
            }
            // 陪护证号
            rspItem.escortNo = rt.escortNo;
            // 状态列表
            rspItem.states = rt.associateEntity.stateRecs.stream().map((x) -> {
                var rec = new EscortStateRec();
                rec.recNo = x.recNo;
                rec.state = x.state;
                rec.recEmplCode = x.recEmplCode;
                rec.recDate = x.recDate;
                return rec;
            }).toList();
            // 动作列表
            rspItem.actions = rt.associateEntity.actionRecs.stream().map((x) -> {
                var rec = new EscortActionRec();
                rec.recNo = x.recNo;
                rec.action = x.action;
                rec.recDate = x.recDate;
                return rec;
            }).toList();

            rspBody.add(rspItem);
        }

        return GsonHelper.ToJson(rspBody);
    }

    @ResponseBody
    @RequestMapping(value = "queryHelperInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String queryHelperInfo(@NotBlank(message = "患者卡号不能为空") String patientCardNo) {
        // 记录日志
        this.logger.info(String.format("查询患者的陪护人信息 %s 的状态", patientCardNo));

        // 调用服务
        var rspBody = new ArrayList<QueryHelperInfoRspBody>();
        for (var rt : this.escortService.queryHelperInfos(patientCardNo)) {
            // 创建元素
            var rspItem = new QueryHelperInfoRspBody();
            // 就诊卡号
            rspItem.cardNo = rt.helperCardNo;
            if (rt.associateEntity.helper != null) {
                var helper = rt.associateEntity.helper;
                // 姓名
                rspItem.name = helper.name;
                // 性别
                rspItem.sex = helper.sex;
                // 年龄
                rspItem.age = this.typeHelper.getAge(helper.birthday).toString();
            }
            if (rt.associateEntity.finIprPrepayIn != null) {
                var fip = rt.associateEntity.finIprPrepayIn;
                if (fip.associateEntity.escortVip != null) {
                    var escortVip = fip.associateEntity.escortVip;
                    // 免费标识
                    rspItem.freeFlag = escortVip.helperCardNo.equals(rt.helperCardNo) ? "1" : "0";
                }
            }
            // 陪护证号
            rspItem.escortNo = rt.escortNo;
            // 状态列表
            rspItem.states = rt.associateEntity.stateRecs.stream().map((x) -> {
                var rec = new EscortStateRec();
                rec.recNo = x.recNo;
                rec.state = x.state;
                rec.recEmplCode = x.recEmplCode;
                rec.recDate = x.recDate;
                return rec;
            }).toList();
            // 动作列表
            rspItem.actions = rt.associateEntity.actionRecs.stream().map((x) -> {
                var rec = new EscortActionRec();
                rec.recNo = x.recNo;
                rec.action = x.action;
                rec.recDate = x.recDate;
                return rec;
            }).toList();

            rspBody.add(rspItem);
        }

        return GsonHelper.ToJson(rspBody);
    }

    @ResponseBody
    @RequestMapping(value = "uploadAnnex", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String uploadAnnex(@NotBlank(message = "陪护人卡号不能为空") String helperCardNo,
            @NotBlank(message = "附件链接不能为空") String url) {
        // 记录日志
        this.logger.info(String.format("上传附件(helperCardNo = %s, url = %s)", helperCardNo, url));

        // 执行服务
        var rt = this.escortService.uploadAnnex(helperCardNo, url);

        return rt.annexNo;
    }

    @ResponseBody
    @RequestMapping(value = "checkAnnex", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void checkAnnex(@NotBlank(message = "附件号不能为空") String annexNo,
            @NotBlank(message = "审核人不能为空") String checker,
            @NotNull(message = "审核结果不能为空") Boolean negativeFlag,
            @NotNull(message = "检验时间不能为空") Date inspectDate) {
        // 记录日志
        this.logger.info(String.format("审核附件(annexNo = %s, checker = %s)", annexNo, checker));

        // 执行服务
        this.escortService.checkAnnex(annexNo, checker, negativeFlag, inspectDate);
    }

    @ResponseBody
    @RequestMapping(value = "queryAnnexInDept", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String queryAnnexInDept(@NotBlank(message = "科室编码不能为空") String deptCode,
            @NotNull(message = "审核标识不能为空") Boolean checked) {
        // 记录日志
        this.logger.info(String.format("查询附件(deptCode = %s, checked = %s)", deptCode, checked.toString()));

        // 结果集
        var rspBody = new ArrayList<QueryAnnexInDeptRspBody>();
        for (var annexInfo : this.escortService.queryAnnexInfoInDept(deptCode, checked)) {
            var rspItem = new QueryAnnexInDeptRspBody();
            rspItem.annexNo = annexInfo.annexNo;
            rspItem.helperName = annexInfo.associateEntity.patient.name;
            rspItem.picUrl = annexInfo.annexUrl;
            rspItem.patientNames = annexInfo.associateEntity.patient.associateEntity.escortedPatients.stream()
                    .map((x) -> x.name).toList();
            if (annexInfo.associateEntity.escortAnnexChk != null) {
                rspItem.negative = annexInfo.associateEntity.escortAnnexChk.negativeFlag;
                rspItem.inspectDate = annexInfo.associateEntity.escortAnnexChk.inspectDate;
            }
            rspBody.add(rspItem);
        }

        return GsonHelper.ToJson(rspBody);
    }
}
