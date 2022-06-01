package com.kaos.skynet.api.data.mapper.inpatient.surgery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsRoomMapperTests {
    @Autowired
    MetOpsRoomMapper metOpsRoomMapper;

    @Test
    void queryMetOpsRoom() {
        metOpsRoomMapper.queryMetOpsRoom("roomId");
    }
}
