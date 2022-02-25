package com.kaos.his.cache.common.config;

import com.kaos.his.cache.Cache;
import com.kaos.his.entity.common.config.ConfigMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigMapCacheTests {
    @Autowired
    Cache<String, ConfigMap> mapCache;

    @Test
    public void getCacheValue() {
        this.mapCache.getValue("EscortRegOffset");
    }
}
