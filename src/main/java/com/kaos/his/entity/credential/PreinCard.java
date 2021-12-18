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
    public String cardNo = null;

    /**
     * 发生序号
     */
    public Integer happenNo = null;

    /**
     * 操作员编码
     */
    public String operCode = null;

    /**
     * 操作时间
     */
    public Date operDate = null;

    /**
     * 预约的科室编码
     */
    public String preDeptCode = null;

    /**
     * 预约的病区
     */
    public String preNurseCellCode = null;

    /**
     * 预约的床号（签床）
     */
    public String preBedNo = null;

    /**
     * 预约的医生编码
     */
    public String preDoctorCode = null;

    /**
     * 预约入院日期
     */
    public Date preDate = null;

    /**
     * 拟定预交金
     */
    public Double preCost = null;

    /**
     * 住院证状态
     */
    public PreinCardStateEnum state = null;

    /**
     * 登记住院的患者实体
     */
    public Patient patient = null;

    /**
     * 操作职员（开立住院证的人）
     */
    public Employee operEmployee = null;

    /**
     * 预约的科室
     */
    public Department preDept = null;

    /**
     * 预约的医生
     */
    public Employee preDoctor = null;
}
