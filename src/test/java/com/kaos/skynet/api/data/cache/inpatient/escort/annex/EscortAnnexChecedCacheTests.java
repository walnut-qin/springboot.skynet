package com.kaos.skynet.api.data.cache.inpatient.escort.annex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortAnnexChecedCacheTests {
    @Autowired
    EscortAnnexCheckCache annexCheckCache;

    @Test
    public void getCacheValue() {
        this.annexCheckCache.get("0000000565");
    }
}
