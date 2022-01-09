package com.kaos.his.common;

import com.kaos.his.enums.ValidStateEnum;
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
        this.patientMapper.queryPatient("000306", null);
        this.patientMapper.queryPatient("000306", ValidStateEnum.作废);
        this.patientMapper.queryPatient("000306", ValidStateEnum.无效);
        this.patientMapper.queryPatient("000306", ValidStateEnum.有效);
    }
}
