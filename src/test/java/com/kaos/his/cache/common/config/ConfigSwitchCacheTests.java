package com.kaos.his.cache.common.config;

import com.kaos.his.entity.common.config.ConfigSwitch;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigSwitchCacheTests {
    @Autowired
    ICache<ConfigSwitch> switchCache;

    @Test
    public void getCacheValue() {
        this.switchCache.getValue("var1");
    }
}
