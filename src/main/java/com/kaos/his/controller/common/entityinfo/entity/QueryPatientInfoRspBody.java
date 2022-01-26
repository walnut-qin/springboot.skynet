package com.kaos.his.controller.common.entityinfo.entity;

import com.kaos.his.enums.common.SexEnum;

public class QueryPatientInfoRspBody {
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
     * 身份证号
     */
    public String identityCardNo = null;

    /**
     * 联系电话
     */
    public String tel = null;
}
