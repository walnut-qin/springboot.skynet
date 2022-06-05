package com.kaos.skynet.api.logic.service.inpatient.escort.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidateServiceTests {
    @Autowired
    ValidateService validateService;

    @Test
    void getHappenNo() {
        validateService.getHappenNo("2009999999", "0123456789");
    }
}
