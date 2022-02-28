package com.kaos.his.controller.inf.common.patient;

import com.google.gson.annotations.SerializedName;
import com.kaos.his.enums.impl.common.SexEnum;

public interface PatientInfo {
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
