package com.kaos.his.service;

import com.kaos.his.entity.personnel.Employee;
import com.kaos.his.mapper.personnel.EmployeeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public Employee GetEmployeeByEmplCode(String emplCode) {
        return employeeMapper.GetEmployeeByEmplCode(emplCode);
    }
}
