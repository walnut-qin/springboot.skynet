package com.kaos.skynet.api.cache.common;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.entity.common.DawnOrgDept;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DawnOrgDeptCacheTests {
    @Autowired
    Cache<String, DawnOrgDept> dawnOrgDeptCache;

    @Test
    public void getCacheValue() {
        this.dawnOrgDeptCache.getValue("1000");
    }
}
