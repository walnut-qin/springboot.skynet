package com.kaos.skynet.mapper.outpatient;

import com.kaos.skynet.enums.impl.common.TransTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinOprRegisterMapperTests {
    @Autowired
    FinOprRegisterMapper registerMapper;

    @Test
    public void queryRegisterRec() {
        this.registerMapper.queryRegisterRec("clinicCode", TransTypeEnum.Positive);
    }
}
