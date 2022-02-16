package com.kaos.his.cache.common;

import com.kaos.his.entity.common.ComPatientInfo;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComPatientInfoCacheTests {
    ICache<ComPatientInfo> comPatientCache = ComPatientInfoCache.getCache();

    @Test
    public void getCacheValue() {
        this.comPatientCache.getValue("2000163720");
    }
}
