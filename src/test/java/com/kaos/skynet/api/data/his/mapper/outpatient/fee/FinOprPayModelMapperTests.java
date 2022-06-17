package com.kaos.skynet.api.data.his.mapper.outpatient.fee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinOprPayModelMapperTests {
    @Autowired
    FinOprPayModelMapper mapper;

    @Test
    public void queryPayModels() {
        this.mapper.queryPayModels(FinOprPayModelMapper.Key.builder().patientId("0001111841").build());
    }
}
