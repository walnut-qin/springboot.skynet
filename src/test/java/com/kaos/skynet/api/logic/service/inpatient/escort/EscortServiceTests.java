package com.kaos.skynet.api.logic.service.inpatient.escort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortServiceTests {
    @Autowired
    EscortService escortService;

    @Test
    void updateState() {
        escortService.updateState("0000046962", null, "test", null);
    }
}
