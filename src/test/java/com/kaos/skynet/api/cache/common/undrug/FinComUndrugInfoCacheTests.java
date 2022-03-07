package com.kaos.skynet.api.cache.common.undrug;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.entity.common.undrug.FinComUndrugInfo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinComUndrugInfoCacheTests {
    @Autowired
    Cache<String, FinComUndrugInfo> dawnOrgDeptCache;

    @Test
    public void getCacheValue() {
        this.dawnOrgDeptCache.getValue("F00000002872");
    }
}
