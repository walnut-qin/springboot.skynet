package com.kaos.his.inpatient.surgery;

import com.kaos.his.mapper.inpatient.surgery.MetOpsRoomMapper;

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
