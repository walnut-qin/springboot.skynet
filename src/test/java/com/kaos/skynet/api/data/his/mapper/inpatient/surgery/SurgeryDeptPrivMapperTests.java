package com.kaos.skynet.api.data.his.mapper.inpatient.surgery;

import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDeptPrivMapper.Key;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SurgeryDeptPrivMapperTests {
    @Autowired
    SurgeryDeptPrivMapper surgeryDeptPrivMapper;

    @Test
    void querySurgeryDeptPriv() {
        surgeryDeptPrivMapper.querySurgeryDeptPriv("xxx", "xxx");
    }

    @Test
    void querySurgeryDeptPrivs() {
        surgeryDeptPrivMapper.querySurgeryDeptPrivs(Key.builder().deptCode("1000").valid(true).build());
    }
}
