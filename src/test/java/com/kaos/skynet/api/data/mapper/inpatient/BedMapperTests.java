package com.kaos.skynet.api.data.mapper.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BedMapperTests {
    @Autowired
    ComBedInfoMapper bedMapper;

    @Test
    void queryBedInfo() {
        this.bedMapper.queryBedInfo("901985");
    }
}
