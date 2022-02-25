package com.kaos.his.controller.inpatient.escort.entity;

import java.util.Date;

import com.kaos.his.enums.impl.inpatient.escort.EscortStateEnum;

public class EscortStateRec {
    /**
     * 状态序号
     */
    public Integer recNo = null;

    /**
     * 状态
     */
    public EscortStateEnum state = null;

    /**
     * 记录员编码
     */
    public String recEmplCode = null;

    /**
     * 记录时间
     */
    public Date recDate = null;
}
