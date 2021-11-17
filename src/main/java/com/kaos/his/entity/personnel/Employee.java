package com.kaos.his.entity.personnel;

import com.kaos.his.entity.organization.Department;

public class Employee extends Citizen {
    /**
     * 员工编码
     */
    public String code;

    /**
     * 归属科室模型
     */
    public Department dept;
}
