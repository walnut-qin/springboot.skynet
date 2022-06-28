package com.kaos.skynet.api.data.his.mapper.inpatient.surgery;

import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryEmplPrivMapper.Key;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SurgeryEmplPrivMapperTests {
    @Autowired
    SurgeryEmplPrivMapper surgeryEmplPrivMapper;

    @Test
    void querySurgeryDeptPriv() {
        surgeryEmplPrivMapper.querySurgeryEmplPriv("xxx", "xxx");
    }

    @Test
    void querySurgeryDeptPrivs() {
        surgeryEmplPrivMapper.querySurgeryEmplPrivs(Key.builder().emplCode("xxx").valid(true).build());
    }
}
