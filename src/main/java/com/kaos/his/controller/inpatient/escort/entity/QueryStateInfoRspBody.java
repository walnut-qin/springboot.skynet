package com.kaos.his.controller.inpatient.escort.entity;

import java.util.Date;

import com.kaos.his.enums.EscortStateEnum;

public class QueryStateInfoRspBody {
    /**
     * 患者卡号
     */
    public String patientCardNo = null;

    /**
     * 陪护卡号
     */
    public String helperCardNo = null;

    /**
     * 注册时间
     */
    public Date regDate = null;

    /**
     * 当前状态
     */
    public EscortStateEnum curState = null;
}
