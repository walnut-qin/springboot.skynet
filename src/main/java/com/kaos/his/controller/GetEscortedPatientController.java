package com.kaos.his.controller;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.his.entity.personnel.Inpatient;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.util.GsonEnumTypeAdapter;
import com.kaos.his.service.EscortService;
import com.kaos.util.DateHelper;

import org.javatuples.Pair;
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
public class GetEscortedPatientController {
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
    }

    @RequestMapping(value = "getActiveEscortedPatient", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String Run(@RequestParam("cardNo") String cardNo) {
        // 声明结果集
        var resultSet = new ArrayList<PatientInfo>();

        // 调取服务
        var activePatients = this.escortService.QueryActiveEscortedPatient(cardNo);

        // 循环赋值
        for (Pair<String, Inpatient> pair : activePatients) {
            var inpatient = pair.getValue1();
            resultSet.add(new PatientInfo() {
                {
                    escortNo = pair.getValue0();
                    cardNo = inpatient.cardNo;
                    name = inpatient.name;
                    sex = inpatient.sex;
                    age = DateHelper.GetAgeDetail(inpatient.birthday);
                    deptName = inpatient.dept.name;
                    bedNo = inpatient.bedNo;
                    patientNo = inpatient.patientNo;
                }
            });
        }

        // 响应json字符串
        Gson gson = new GsonBuilder().registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class))
                .create();
        return gson.toJson(resultSet);
    }
}
