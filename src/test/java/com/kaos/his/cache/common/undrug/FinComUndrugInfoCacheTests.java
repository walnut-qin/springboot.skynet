package com.kaos.his.cache.common.undrug;

import com.kaos.his.entity.common.undrug.FinComUndrugInfo;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinComUndrugInfoCacheTests {
    @Autowired
    ICache<FinComUndrugInfo> dawnOrgDeptCache;

    @Test
    public void getCacheValue() {
        this.dawnOrgDeptCache.getValue("F00000002872");
    }
}
