package com.kaos.skynet.api.data.mapper.common;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DawnOrgEmplMapperTests {
    @Autowired
    DawnOrgEmplMapper employeeMapper;

    @Test
    public void queryEmpl() throws Exception {
        this.employeeMapper.queryEmpl(null);
        this.employeeMapper.queryEmpl("000306");
    }

    @Test
    public void queryEmpls() throws Exception {
        this.employeeMapper.queryEmpls(null, null);
        this.employeeMapper.queryEmpls(SexEnum.Female, Lists.newArrayList(ValidEnum.VALID));
    }
}
