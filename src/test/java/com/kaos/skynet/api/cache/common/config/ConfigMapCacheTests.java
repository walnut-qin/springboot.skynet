package com.kaos.skynet.api.cache.common.config;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.entity.common.config.ConfigMap;

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
