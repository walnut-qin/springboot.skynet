package com.kaos.skynet.api.data.cache.common.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigSwitchCacheTests {
    @Autowired
    ConfigSwitchCache configSwitchCache;

    @Test
    void get() {
        configSwitchCache.get("switch");
    }
}
