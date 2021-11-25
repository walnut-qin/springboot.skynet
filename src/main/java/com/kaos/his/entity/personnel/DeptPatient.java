package com.kaos.his.entity.personnel;

import java.util.Date;

import com.kaos.his.entity.organization.Department;

/**
 * 科室患者实体
 */
public class Deptpatient extends Patient {
    /**
     * 操作员编码
     */
    public String operCode = null;

    /**
     * 操作时间
     */
    public Date operDate = null;

    /**
     * 科室编码
     */
    public String deptCode = null;

    /**
     * 操作员（门诊患者：挂号员；住院患者：住院登记员）
     */
    public Employee operEmployee = null;

    /**
     * 科室信息
     */
    public Department dept = null;
}
