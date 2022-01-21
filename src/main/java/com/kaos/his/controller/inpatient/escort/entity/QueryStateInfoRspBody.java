package com.kaos.his.controller.inpatient.escort.entity;

import java.util.Date;

public class QueryStateInfoRspBody {
    /**
     * 患者卡号
     */
    public String patientCardNo = null;

    /**
     * 陪护卡号
     */
    public String escortCardNo = null;

    /**
     * 注册时间
     */
    public Date regDate = null;

    /**
     * 当前状态
     */
    public String state = null;
}
