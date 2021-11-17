package com.kaos.his.entity.personnel;

import com.kaos.his.entity.organization.Department;

/**
 * 科室患者实体
 */
public class DeptPatient extends Patient {
    /**
     * 指定科室的操作员（门诊患者：挂号员；住院患者：住院登记员）
     */
    public String operCode;

    /**
     * 科室信息
     */
    public Department dept;
}
