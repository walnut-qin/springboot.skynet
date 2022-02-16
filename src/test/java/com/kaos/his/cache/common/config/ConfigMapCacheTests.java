package com.kaos.his.cache.common.config;

import com.kaos.his.entity.common.config.ConfigMap;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigMapCacheTests {
    ICache<ConfigMap> mapCache = ConfigMapCache.getCache();

    @Test
    public void getCacheValue() {
        this.mapCache.getValue(null);
    }
}
