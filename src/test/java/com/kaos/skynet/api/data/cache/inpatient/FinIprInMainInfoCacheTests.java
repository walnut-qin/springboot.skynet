package com.kaos.skynet.api.data.cache.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIprInMainInfoCacheTests {
    @Autowired
    FinIprInMainInfoCache inMainInfoCache;

    @Test
    public void getCacheValue() {
        inMainInfoCache.get("ZY010000705856");
    }
}
