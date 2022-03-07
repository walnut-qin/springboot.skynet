package com.kaos.skynet.api.mapper.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComPatientInfoMapperTests {
    @Autowired
    ComPatientInfoMapper patientMapper;

    @Test
    public void queryPatient() {
        this.patientMapper.queryPatientInfo(null);
        this.patientMapper.queryPatientInfo("2009999999");
    }
}
