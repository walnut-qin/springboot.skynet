package com.kaos.his.mapper.common;

import com.kaos.his.entity.common.Department;
import com.kaos.his.enums.ValidStateEnum;

public interface DepartmentMapper {
    /**
     * 主键查询
     * 
     * @param deptCode 科室编码
     * @param valid    有效标识，若为null，则不纳入判断
     * @return
     */
    Department queryDepartment(String deptCode, ValidStateEnum valid);
}
