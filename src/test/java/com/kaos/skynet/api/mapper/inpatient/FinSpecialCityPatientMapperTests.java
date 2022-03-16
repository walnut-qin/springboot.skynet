package com.kaos.skynet.api.mapper.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinSpecialCityPatientMapperTests {
    @Autowired
    FinSpecialCityPatientMapper mapper;

    @Test
    public void querySpecialCityPatient() {
        this.mapper.querySpecialCityPatient(null);
        this.mapper.querySpecialCityPatient("ZY010001369394");
    }
}
