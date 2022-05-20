package com.kaos.skynet.api.data.cache.common.config;

import com.kaos.skynet.api.data.cache.common.config.ConfigMultiMapCache.Key;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigMultiMapCacheTests {
    @Autowired
    ConfigMultiMapCache.MasterCache configMultiMapMasterCache;

    @Autowired
    ConfigMultiMapCache.SlaveCache configMultiMapSlaveCache;

    @Test
    void get() {
        this.configMultiMapMasterCache.get(new Key("GcpDept", "2161"));
        this.configMultiMapSlaveCache.get("GcpDept");
    }
}
