package com.kaos.skynet.api.data.his.mapper.common;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.his.enums.SexEnum;
import com.kaos.skynet.api.data.his.enums.ValidEnum;
import com.kaos.skynet.api.data.his.mapper.common.DawnOrgEmplMapper.Key;

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
        employeeMapper.queryEmpls(Key.builder().sex(SexEnum.Male).valids(Lists.newArrayList(ValidEnum.VALID)).build());
    }
}
