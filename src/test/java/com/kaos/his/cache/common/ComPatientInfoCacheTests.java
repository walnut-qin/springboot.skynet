package com.kaos.his.cache.common;

import com.kaos.his.cache.Cache;
import com.kaos.his.entity.common.ComPatientInfo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComPatientInfoCacheTests {
    @Autowired
    Cache<String, ComPatientInfo> comPatientCache;

    @Test
    public void getCacheValue() {
        this.comPatientCache.getValue("2000163720");
    }
}
