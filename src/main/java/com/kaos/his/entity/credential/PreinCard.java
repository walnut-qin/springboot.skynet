package com.kaos.his.entity.credential;

import java.util.Date;

import com.kaos.his.entity.organization.Department;
import com.kaos.his.entity.personnel.Employee;
import com.kaos.his.entity.personnel.Patient;
import com.kaos.his.enums.PreinCardStateEnum;

public class PreinCard {
    /**
     * 就诊卡号
     */
    public String cardNo;

    /**
     * 发生序号
     */
    public Integer happenNo;

    /**
     * 登记住院的患者实体
     */
    public Patient patient;

    /**
     * 操作职员（开立住院证的人）
     */
    public Employee operEmployee;

    /**
     * 操作时间
     */
    public Date operDate;

    /**
     * 预约的科室
     */
    public Department preDept;

    /**
     * 预约的病区
     */
    public String preNurseCellCode;

    /**
     * 预约的床号（签床）
     */
    public String preBedNo;

    /**
     * 预约的医生
     */
    public Employee preDoctor;

    /**
     * 预约入院日期
     */
    public Date preDate;

    /**
     * 拟定预交金
     */
    public Double preCost;

    /**
     * 住院证状态
     */
    public PreinCardStateEnum state;
}
