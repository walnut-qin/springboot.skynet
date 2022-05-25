package com.kaos.skynet.api.data.cache.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DawnOrgDeptCacheTests {
    @Autowired
    DawnOrgDeptCache deptCache;

    @Test
    public void getCacheValue() {
        this.deptCache.get("1000");
        this.deptCache.getClone("1000");
    }
}
