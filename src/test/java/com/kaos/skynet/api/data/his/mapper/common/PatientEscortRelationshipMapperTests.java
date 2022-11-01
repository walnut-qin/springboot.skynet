package com.kaos.skynet.api.data.his.mapper.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PatientEscortRelationshipMapperTests {
    @Autowired
    PatientEscortRelationshipMapper mapper;

    @Test
    void queryEmpl() {
        mapper.queryRelationship("2550616378", "2550536879");
    }
}
