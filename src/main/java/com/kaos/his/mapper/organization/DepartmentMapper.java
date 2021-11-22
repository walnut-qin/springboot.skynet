package com.kaos.his.mapper.organization;

import com.kaos.his.entity.organization.Department;

import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentMapper {
    /**
     * 根据科室编码查询科室实体
     * 
     * @param deptCode 科室编码
     * @return
     */
    Department QueryDepartment(String deptCode);
}
