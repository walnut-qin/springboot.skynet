package com.kaos.skynet.api.data.cache.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinSpecialCityPatientCacheTests {
    @Autowired
    FinSpecialCityPatientCache specialCityPatientCache;

    @Test
    void get() {
        specialCityPatientCache.get("ZY010000000001");
    }
}
