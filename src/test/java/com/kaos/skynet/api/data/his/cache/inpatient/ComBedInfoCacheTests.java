package com.kaos.skynet.api.data.his.cache.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComBedInfoCacheTests {
    @Autowired
    ComBedInfoCache bedInfoCache;

    @Test
    public void getCacheValue() {
        this.bedInfoCache.get("901985");
    }
}
