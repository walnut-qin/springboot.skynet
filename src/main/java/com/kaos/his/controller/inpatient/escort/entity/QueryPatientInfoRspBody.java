package com.kaos.his.controller.inpatient.escort.entity;

import java.util.List;

import com.kaos.his.entity.inpatient.escort.EscortActionRec;
import com.kaos.his.entity.inpatient.escort.EscortStateRec;
import com.kaos.his.enums.SexEnum;

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
     * 科室
     */
    public String deptName = null;

    /**
     * 床号
     */
    public String bedNo = null;

    /**
     * 住院号
     */
    public String patientNo = null;

    /**
     * 免费标识
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
     * 动作列表
     */
    public List<EscortActionRec> actions = null;
}
