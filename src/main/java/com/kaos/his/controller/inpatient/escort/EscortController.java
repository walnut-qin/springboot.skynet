package com.kaos.his.controller.inpatient.escort;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kaos.his.controller.inpatient.escort.entity.QueryPatientInfoRspBody;
import com.kaos.his.controller.inpatient.escort.entity.QueryStateInfoRspBody;
import com.kaos.his.entity.inpatient.Inpatient;
import com.kaos.his.service.inpatient.EscortService;
import com.kaos.util.DateHelper;
import com.kaos.util.GsonHelper;
import com.kaos.util.ListHelper;

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

    /**
     * 将字符串映射到锁索引
     * 
     * @param input
     * @return
     */
    private Object mapToLock(String input, List<Object> locks) {
        // 计算索引
        input = input.replaceAll("[^0-9]", "");
        var idx = Long.valueOf(input) % locks.size();
        this.logger.info(String.format("映射到锁对象: %s -> %d", input, idx));

        return locks.get((int) idx);
    }

    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void register(@NotBlank(message = "患者卡号不能为空") String patientCardNo,
            @NotBlank(message = "陪护人卡号不能为空") String helperCardNo,
            @NotBlank(message = "操作员编码不能为空") String emplCode,
            @NotNull(message = "备注不能为空") String remark) {
        // 记录日志
        this.logger.info(String.format("登记陪护证(patientCardNo = %s, helperCardNo = %s)", patientCardNo, helperCardNo));

        try {
            synchronized (this.mapToLock(patientCardNo, patientLocks)) {
                this.logger.info("加锁");
                try {
                    synchronized (this.mapToLock(helperCardNo, helperLocks)) {
                        this.logger.info("加锁");
                        // 执行服务
                        this.escortService.registerEscort(patientCardNo, helperCardNo, emplCode, remark);
                    }
                } finally {
                    this.logger.info("解锁");
                }
            }
        } finally {
            this.logger.info("解锁");
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
        rspBody.helperCardNo = srvRt.helperCardNo;
        if (srvRt.associateEntity.stateRecs != null) {
            rspBody.regDate = srvRt.associateEntity.stateRecs.get(0).recDate;
            rspBody.curState = ListHelper.GetLast(srvRt.associateEntity.stateRecs).state;
        }

        return GsonHelper.ToJson(rspBody);
    }

    @ResponseBody
    @RequestMapping(value = "queryPatientInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String queryPatientInfo(@NotBlank(message = "陪护人卡号不能为空") String helperCardNo) {
        // 记录日志
        this.logger.info(String.format("查询陪护的患者信息 %s 的状态", helperCardNo));

        // 调用服务
        var srvRs = this.escortService.queryPatientInfos(helperCardNo);

        // 构造响应
        var rspBody = new ArrayList<QueryPatientInfoRspBody>();
        srvRs.forEach((rt) -> {
            // 创建元素
            var rspItem = new QueryPatientInfoRspBody();
            // 就诊卡号
            rspItem.cardNo = rt.patientCardNo;
            // 姓名
            rspItem.name = rt.associateEntity.finIprPrepayIn.associateEntity.patient.name;
            // 性别
            rspItem.sex = rt.associateEntity.finIprPrepayIn.associateEntity.patient.sex;
            // 年龄
            rspItem.age = DateHelper.GetAgeDetail(rt.associateEntity.finIprPrepayIn.associateEntity.patient.birthday);

            if (rt.associateEntity.finIprPrepayIn.associateEntity.patient instanceof Inpatient) {
                var inpatient = (Inpatient) rt.associateEntity.finIprPrepayIn.associateEntity.patient;
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
                if (rt.associateEntity.finIprPrepayIn.associateEntity.preDept == null) {
                    rspItem.deptName = rt.associateEntity.finIprPrepayIn.preDeptCode;
                } else {
                    rspItem.deptName = rt.associateEntity.finIprPrepayIn.associateEntity.preDept.deptName;
                }
                // 床号
                if (rt.associateEntity.finIprPrepayIn.associateEntity.bedInfo == null) {
                    rspItem.bedNo = rt.associateEntity.finIprPrepayIn.bedNo;
                } else {
                    rspItem.bedNo = rt.associateEntity.finIprPrepayIn.associateEntity.bedInfo.getBriefBedNo();
                }
            }

            // 免费标识
            rspItem.freeFlag = rt.associateEntity.finIprPrepayIn.associateEntity.escortVip.helperCardNo
                    .equals(rt.helperCardNo) ? "1" : "0";
            // 陪护证号
            rspItem.escortNo = rt.escortNo;
        });

        return GsonHelper.ToJson(rspBody);
    }
}
