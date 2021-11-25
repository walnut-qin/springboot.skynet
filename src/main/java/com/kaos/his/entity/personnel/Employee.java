package com.kaos.his.entity.personnel;

import com.kaos.his.entity.organization.Department;

public class Employee extends Citizen {
    /**
     * 员工编码
     */
    public String code = null;

    /**
     * 科室编码
     */
    public String deptCode = null;

    /**
     * 归属科室模型
     */
    public Department dept = null;
}
