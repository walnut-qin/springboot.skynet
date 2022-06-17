package com.kaos.skynet.api.data.his.mapper.outpatient;

import com.kaos.skynet.api.data.his.enums.TransTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinOprRegisterMapperTests {
    @Autowired
    FinOprRegisterMapper registerMapper;

    @Test
    public void queryRegisterRec() {
        this.registerMapper.queryRegister("clinicCode", TransTypeEnum.Positive);
    }
}
