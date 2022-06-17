package com.kaos.skynet.api.data.his.mapper.inpatient.escort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortVipMapperTests {
    @Autowired
    EscortVipMapper vipMapper;

    @Test
    void queryEscortVip() {
        vipMapper.queryEscortVip("2000003605", 10);
    }
}
