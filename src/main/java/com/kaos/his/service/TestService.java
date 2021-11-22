package com.kaos.his.service;

import com.kaos.his.entity.organization.Department;
import com.kaos.his.mapper.organization.DepartmentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    DepartmentMapper departmentMapper;

    public Department GetDepartment(String deptCode) {
        return this.departmentMapper.QueryDepartment(deptCode);
    }
}
