package com.kaos.skynet.api.data.his.mapper.inpatient.surgery;

import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDictMapper.Key;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SurgeryDictMapperTests {
    @Autowired
    SurgeryDictMapper surgeryDictMapper;

    @Test
    void querySurgeryDict() {
        surgeryDictMapper.querySurgeryDict("xxx");
    }

    @Test
    void querySurgeryDicts() {
        surgeryDictMapper.querySurgeryDicts(Key.builder().valid(true).build());
    }
}
