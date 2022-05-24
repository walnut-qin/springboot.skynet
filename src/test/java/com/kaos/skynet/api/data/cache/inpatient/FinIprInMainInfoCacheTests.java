package com.kaos.skynet.api.data.cache.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIprInMainInfoCacheTests {
    @Autowired
    FinIprInMainInfoCache.MasterCache inMainInfoMasterCache;

    @Autowired
    FinIprInMainInfoCache.SlaveCache inMainInfoSlaveCache;

    @Test
    public void getCacheValue() {
        inMainInfoMasterCache.get("ZY010000705856");
        inMainInfoSlaveCache.get(FinIprInMainInfoCache.SlaveCacheKey.builder()
                .cardNo("2000003605")
                .happenNo(10)
                .build());
    }
}
