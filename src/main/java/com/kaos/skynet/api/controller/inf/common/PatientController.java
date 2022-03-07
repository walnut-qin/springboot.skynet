package com.kaos.skynet.api.controller.inf.common;

import javax.validation.constraints.NotBlank;

import com.google.gson.annotations.SerializedName;
import com.kaos.skynet.enums.impl.common.SexEnum;

import org.springframework.validation.annotation.Validated;

@Validated
public interface PatientController {
    /**
     * 查询患者基本信息
     * @param cardNo
     * @return
     */
    QueryPatientInfoRsp queryPatientInfo(@NotBlank(message = "就诊卡号不能为空") String cardNo);

    public static class QueryPatientInfoRsp {
        /**
         * 就诊卡号
         */
        public String cardNo = null;

        /**
         * 患者姓名
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
         * 身份证号
         */
        @SerializedName(value = "identityCardNo")
        public String idenNo = null;

        /**
         * 电话号码
         */
        public String tel = null;
    }
}
