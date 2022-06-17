package com.kaos.skynet.api.data.his.cache.common.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigMapCacheTests {
    @Autowired
    ConfigMapCache configMapCache;

    @Test
    void get() {
        this.configMapCache.get("test");
    }
}
