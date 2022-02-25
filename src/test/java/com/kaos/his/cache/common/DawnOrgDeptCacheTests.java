package com.kaos.his.cache.common;

import com.kaos.his.cache.Cache;
import com.kaos.his.entity.common.DawnOrgDept;

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
