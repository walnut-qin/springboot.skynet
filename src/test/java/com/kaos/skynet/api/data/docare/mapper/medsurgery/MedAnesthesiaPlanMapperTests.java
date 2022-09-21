package com.kaos.skynet.api.data.docare.mapper.medsurgery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MedAnesthesiaPlanMapperTests {
    @Autowired
    MedAnesthesiaPlanMapper anesthesiaPlanMapper;

    @Test
    void queryAnesthesiaPlan() {
        // anesthesiaPlanMapper.queryAnesthesiaPlan("0000782112", 1, 1);
    }
}
