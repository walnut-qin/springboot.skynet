package com.kaos.skynet.api.cache.inpatient.escort;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.entity.inpatient.escort.EscortAnnexInfo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortAnnexChecedCacheTests {
    @Autowired
    Cache<String, EscortAnnexInfo> cache;

    @Test
    public void getCacheValue() {
        this.cache.getValue("2551917678");
    }
}
