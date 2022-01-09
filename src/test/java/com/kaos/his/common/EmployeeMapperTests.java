package com.kaos.his.common;

import com.kaos.his.enums.ValidStateEnum;
import com.kaos.his.mapper.common.EmployeeMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeMapperTests {
    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void queryEmployee() throws Exception {
        this.employeeMapper.queryEmployee("000306", null);
        this.employeeMapper.queryEmployee("000306", ValidStateEnum.作废);
        this.employeeMapper.queryEmployee("000306", ValidStateEnum.无效);
        this.employeeMapper.queryEmployee("000306", ValidStateEnum.有效);
    }

    @Test
    public void queryInformalEmployee() throws Exception {
        this.employeeMapper.queryInformalEmployee("W00801", null);
        this.employeeMapper.queryInformalEmployee("W00801", ValidStateEnum.作废);
        this.employeeMapper.queryInformalEmployee("W00801", ValidStateEnum.无效);
        this.employeeMapper.queryInformalEmployee("W00801", ValidStateEnum.有效);
    }
}
