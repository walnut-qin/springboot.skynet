package com.kaos.his.mapper.inpatient.escort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortStateRecMapperTests {
    @Autowired
    EscortStateRecMapper escortStateRecMapper;

    @Test
    public void queryInitState() {
        this.escortStateRecMapper.queryInitState("0000000018");
    }

    @Test
    public void queryCurState() {
        this.escortStateRecMapper.queryCurState("0000000018");
    }

    @Test
    public void queryStates() {
        this.escortStateRecMapper.queryStates("0000000018");
    }
}
