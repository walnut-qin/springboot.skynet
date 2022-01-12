package com.kaos.his.inpatient.escort;

import com.kaos.his.mapper.inpatient.escort.EscortVipMapper;

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
