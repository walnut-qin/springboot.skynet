package com.kaos.skynet.api.data.cache.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComPatientInfoCacheTests {
    @Autowired
    ComPatientInfoCache comPatientCache;

    @Test
    public void getCacheValue() {
        this.comPatientCache.get("2000163720");
    }
}
