package com.kaos.skynet.api.logic.service.inpatient.escort.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StateServiceTests {
    @Autowired
    StateService stateService;

    @Test
    void queryEscortState() {
        stateService.queryEscortState("0000030651");
    }
}
