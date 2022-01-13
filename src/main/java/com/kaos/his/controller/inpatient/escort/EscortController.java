package com.kaos.his.controller.inpatient.escort;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

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
        }
    };

    /**
     * 陪护证服务
     */
    @Autowired
    EscortService escortService;

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
    @RequestMapping(value = "queryStateInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String queryPatientInfo(@NotBlank(message = "陪护人卡号不能为空") String helperCardNo) {
        // 记录日志
        this.logger.info(String.format("查询陪护的患者信息 %s 的状态", helperCardNo));

        // 调用服务
        var srvRs = this.escortService.queryPatientInfos(helperCardNo);

        // 构造响应
        var rspBody = new ArrayList<QueryPatientInfoRspBody>();
        for (var srvRt : srvRs) {
            var item = new QueryPatientInfoRspBody();

            var fip = srvRt.associateEntity.finIprPrepayIn;
            item.cardNo = srvRt.patientCardNo;
            item.name = fip.associateEntity.patient.name;
            item.sex = fip.associateEntity.patient.sex;
            item.age = DateHelper.GetAgeDetail(fip.associateEntity.patient.birthday);
            if (fip.associateEntity.patient instanceof Inpatient) {
                Inpatient inpatient = (Inpatient) fip.associateEntity.patient;
                if (inpatient.associateEntity.stayedDept == null) {
                    item.deptName = inpatient.stayedDeptCode;
                } else {
                    item.deptName = inpatient.associateEntity.stayedDept.deptName;
                }
                if (inpatient.associateEntity.bed == null) {
                    item.bedNo = inpatient.bedNo;
                } else {
                    item.bedNo = inpatient.associateEntity.bed.getBriefBedNo();
                }
                item.patientNo = inpatient.patientNo;
            } else {
                if (fip.associateEntity.preDept == null) {
                    item.deptName = fip.preDeptCode;
                } else {
                    item.deptName = fip.associateEntity.preDept.deptName;
                }
                if (fip.associateEntity.bedInfo == null) {
                    item.bedNo = fip.bedNo;
                } else {
                    item.bedNo = fip.associateEntity.bedInfo.getBriefBedNo();
                }
            }
            item.freeFlag = fip.associateEntity.escortVip.helperCardNo.equals(srvRt.helperCardNo) ? "1" : "0";
            item.escortNo = srvRt.escortNo;
            item.states = srvRt.associateEntity.stateRecs;
            item.actions = srvRt.associateEntity.actionRecs;

            rspBody.add(item);
        }

        return GsonHelper.ToJson(rspBody);
    }
}
