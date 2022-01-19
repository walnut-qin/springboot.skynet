package com.kaos.his.common;

import com.kaos.his.mapper.common.PatientMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PatientMapperTests {
    @Autowired
    PatientMapper patientMapper;

    @Test
    public void queryPatient() {
        this.patientMapper.queryPatient("2009999999");
    }
}
