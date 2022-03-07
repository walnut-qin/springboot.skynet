package com.kaos.skynet.mapper.inpatient.escort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortVipMapperTests {
    @Autowired
    EscortVipMapper escortVipMapper;

    @Test
    public void queryEscortVip() {
        this.escortVipMapper.queryEscortVip("patientCardNo", 1);
    }
}
