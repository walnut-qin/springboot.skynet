package com.kaos.his.controller.inpatient.escort.entity;

import java.util.List;

import com.kaos.his.enums.impl.common.SexEnum;

public class QueryHelperInfoRspBody {
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
     * 免费标识： 0 - 不免费 1 - 免费
     */
    public String freeFlag = null;

    /**
     * 陪护证号
     */
    public String escortNo = null;

    /**
     * 状态列表
     */
    public List<EscortStateRec> states = null;

    /**
     * 行为列表
     */
    public List<EscortActionRec> actions = null;
}
