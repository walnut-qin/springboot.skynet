package com.kaos.his.cache.common.config;

import com.kaos.his.entity.common.config.ConfigSwitch;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigSwitchCacheTests {
    ICache<ConfigSwitch> switchCache = ConfigSwitchCache.getCache();

    @Test
    public void getCacheValue() {
        this.switchCache.getValue(null);
    }
}
