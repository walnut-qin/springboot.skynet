package com.kaos.his.cache.common;

import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DawnOrgDeptCacheTests {
    ICache<DawnOrgDept> dawnOrgDeptCache = DawnOrgDeptCache.getCache();

    @Test
    public void getCacheValue() {
        this.dawnOrgDeptCache.getValue("1000");
    }
}
