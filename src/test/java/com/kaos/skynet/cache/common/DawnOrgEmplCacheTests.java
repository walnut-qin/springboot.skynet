package com.kaos.skynet.cache.common;

import com.kaos.skynet.cache.Cache;
import com.kaos.skynet.entity.common.DawnOrgEmpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DawnOrgEmplCacheTests {
    @Autowired
    Cache<String, DawnOrgEmpl> dawnOrgEmplCache;

    @Test
    public void getCacheValue() {
        this.dawnOrgEmplCache.getValue("0306");
    }
}
