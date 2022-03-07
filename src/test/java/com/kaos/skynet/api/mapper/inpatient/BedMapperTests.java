package com.kaos.skynet.api.mapper.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BedMapperTests {
    @Autowired
    ComBedInfoMapper bedMapper;

    @Test
    public void queryBed() {
        this.bedMapper.queryBedInfo("901985");
    }
}
