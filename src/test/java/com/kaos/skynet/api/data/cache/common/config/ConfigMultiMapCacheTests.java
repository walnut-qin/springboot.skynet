package com.kaos.skynet.api.data.cache.common.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kaos.skynet.api.data.cache.common.config.ConfigMultiMapCache.MasterCache.Key;

@SpringBootTest
public class ConfigMultiMapCacheTests {
    @Autowired
    ConfigMultiMapCache configMultiMapCache;

    @Test
    void get() {
        configMultiMapCache.masterCache.get(new Key("GcpDept", "2161"));
        configMultiMapCache.slaveCache.get("GcpDept");
    }
}
