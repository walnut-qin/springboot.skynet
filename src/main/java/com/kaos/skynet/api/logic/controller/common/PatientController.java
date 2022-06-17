package com.kaos.skynet.api.logic.controller.common;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.constraints.NotBlank;

import com.google.gson.annotations.SerializedName;
import com.kaos.skynet.api.data.his.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.his.enums.SexEnum;
import com.kaos.skynet.api.logic.controller.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping({ "api/common/patient", "/ms/common/patient", "/ms/common/entityinfo" })
public class PatientController {
    /**
     * 实体信息服务
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @RequestMapping(value = "queryPatientInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    public QueryPatientInfoRsp queryPatientInfo(@NotBlank(message = "就诊卡号不能为空") String cardNo) {
        // 记录日志
        log.info(String.format("查询患者信息(cardNo = %s)", cardNo));

        // 调用服务
        var patient = this.patientInfoCache.get(cardNo);
        if (patient == null) {
            throw new RuntimeException("就诊卡不存在!");
        }

        // 构造响应体
        var rspBuilder = QueryPatientInfoRsp.builder();
        rspBuilder.cardNo(patient.getCardNo());
        rspBuilder.name(patient.getName());
        rspBuilder.sex(patient.getSex());
        rspBuilder.age(Period.between(patient.getBirthday().toLocalDate(), LocalDate.now()));
        rspBuilder.idenNo(patient.getIdentityCardNo());
        rspBuilder.tel(patient.getHomeTel());

        return rspBuilder.build();
    }

    /**
     * 患者信息
     */
    @Getter
    @Builder
    public static class QueryPatientInfoRsp {
        /**
         * 就诊卡号
         */
        private String cardNo;

        /**
         * 患者姓名
         */
        private String name;

        /**
         * 性别
         */
        private SexEnum sex;

        /**
         * 年龄
         */
        private Period age;

        /**
         * 身份证号
         */
        @SerializedName(value = "identityCardNo")
        private String idenNo;

        /**
         * 电话号码
         */
        private String tel;
    }
}
