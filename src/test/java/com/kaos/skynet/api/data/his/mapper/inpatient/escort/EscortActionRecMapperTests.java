package com.kaos.skynet.api.data.his.mapper.inpatient.escort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortActionRecMapperTests {
    @Autowired
    EscortActionRecMapper actionRecMapper;

    @Test
    void queryActions() {
        actionRecMapper.queryActions("0000001170");
    }
}
