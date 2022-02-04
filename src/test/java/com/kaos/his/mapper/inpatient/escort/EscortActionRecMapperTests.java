package com.kaos.his.mapper.inpatient.escort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortActionRecMapperTests {
    @Autowired
    EscortActionRecMapper escortActionRecMapper;

    @Test
    public void queryActions() {
        this.escortActionRecMapper.queryActions(null);
        this.escortActionRecMapper.queryActions("0000000018");
    }
}
