package com.kaos.skynet.cache.common.config;

import com.kaos.skynet.cache.Cache;
import com.kaos.skynet.entity.common.config.ConfigSwitch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigSwitchCacheTests {
    @Autowired
    Cache<String, ConfigSwitch> switchCache;

    @Test
    public void getCacheValue() {
        this.switchCache.getValue("var1");
    }
}
