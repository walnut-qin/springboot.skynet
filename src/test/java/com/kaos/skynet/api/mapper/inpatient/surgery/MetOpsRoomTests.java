package com.kaos.skynet.api.mapper.inpatient.surgery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsRoomTests {
    @Autowired
    MetOpsRoomMapper metOpsRoomMapper;

    @Test
    public void queryMetOpsRoom() {
        this.metOpsRoomMapper.queryMetOpsRoom("14");
    }
}
