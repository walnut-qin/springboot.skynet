package com.kaos.his.mapper;

import com.kaos.his.entity.personnel.Employee;

import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeMapper {
    /**
     * 根据职工编码获取职工实体
     * 
     * @param id 职工编号
     * @return 职工实体
     */
    Employee GetEmployeeByEmplCode(String emplCode);
}
