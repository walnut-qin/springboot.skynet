package com.kaos.skynet.api.data.mapper.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinSpecialCityPatientMapperTests {
    @Autowired
    FinSpecialCityPatientMapper specialCityPatientMapper;

    @Test
    void querySpecialCityPatient() {
        specialCityPatientMapper.querySpecialCityPatient("ZY010000000001");
    }
}
