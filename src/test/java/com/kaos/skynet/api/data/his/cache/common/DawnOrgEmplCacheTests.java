package com.kaos.skynet.api.data.his.cache.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DawnOrgEmplCacheTests {
    @Autowired
    DawnOrgEmplCache emplCache;

    @Test
    public void getCacheValue() {
        this.emplCache.get("0306");
    }
}
