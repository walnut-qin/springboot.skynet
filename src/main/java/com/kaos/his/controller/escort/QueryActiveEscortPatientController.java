package com.kaos.his.controller.escort;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.entity.personnel.Inpatient;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.util.GsonEnumTypeAdapter;
import com.kaos.his.service.EscortService;
import com.kaos.util.DateHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 根据陪护人卡号，查询有效的被陪护患者信息
 */
@RestController
@RequestMapping("/webApi")
public class QueryActiveEscortPatientController {
    /**
     * 陪护系统服务
     */
    @Autowired
    EscortService escortService;

    /**
     * 被陪护患者信息
     */
    public static class PatientInfo {
        /**
         * 陪护证编号
         */
        public String escortNo = null;

        /**
         * 就诊卡号
         */
        public String cardNo = null;

        /**
         * 姓名
         */
        public String name = null;

        /**
         * 性别
         */
        public SexEnum sex = null;

        /**
         * 年龄
         */
        public String age = null;

        /**
         * 科室名称
         */
        public String deptName = null;

        /**
         * 床号
         */
        public String bedNo = null;

        /**
         * 患者住院号
         */
        public String patientNo = null;

        /**
         * 免费标识： 0 - 不免费 1 - 免费
         */
        public String freeFlag = null;
    }

    @RequestMapping(value = "queryActiveEscortPatient", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String Run(@RequestParam("helperCardNo") String helperCardNo) {
        // 入参判断
        if (helperCardNo == null) {
            throw new RuntimeException("陪护人卡号不能为空");
        }

        // 调取服务
        var escorts = this.escortService.QueryHelperRegisteredEscorts(helperCardNo);

        // 循环赋值
        var resultSet = new ArrayList<PatientInfo>();
        for (EscortCard escort : escorts) {
            // 创建新实体元素
            var patientInfo = new PatientInfo();
            patientInfo.escortNo = escort.escortNo;
            patientInfo.cardNo = escort.preinCard.patient.cardNo;
            patientInfo.name = escort.preinCard.patient.name;
            patientInfo.sex = escort.preinCard.patient.sex;
            patientInfo.age = DateHelper.GetAgeDetail(escort.preinCard.patient.birthday);
            if (escort.preinCard.patient instanceof Inpatient) {
                patientInfo.deptName = ((Inpatient) escort.preinCard.patient).dept.name;
                patientInfo.bedNo = ((Inpatient) escort.preinCard.patient).bedNo;
                patientInfo.patientNo = ((Inpatient) escort.preinCard.patient).patientNo;
            } else {
                patientInfo.deptName = escort.preinCard.preDept.name;
                patientInfo.bedNo = escort.preinCard.preBedNo;
                patientInfo.patientNo = null;
            }
            patientInfo.freeFlag = escort.helperCardNo.equals(escort.escortVip.helperCardNo) ? "1" : "0";

            // 添加实体元素
            resultSet.add(patientInfo);
        }

        // 响应json字符串
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class)).create();
        return gson.toJson(resultSet);
    }
}
