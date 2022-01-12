package com.kaos.his.inpatient.escort;

import com.kaos.his.mapper.inpatient.escort.EscortActionRecMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortActionRecMapperTests {
    @Autowired
    EscortActionRecMapper escortActionRecMapper;

    @Test
    public void queryLastAction() {
        this.escortActionRecMapper.queryLastAction("0000000018");
    }

    @Test
    public void queryActions() {
        this.escortActionRecMapper.queryActions("0000000018");
    }
}
