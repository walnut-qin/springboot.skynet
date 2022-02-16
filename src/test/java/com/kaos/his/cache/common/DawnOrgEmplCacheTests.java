package com.kaos.his.cache.common;

import com.kaos.his.entity.common.DawnOrgEmpl;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DawnOrgEmplCacheTests {
    @Autowired
    ICache<DawnOrgEmpl> dawnOrgEmplCache;

    @Test
    public void getCacheValue() {
        this.dawnOrgEmplCache.getValue("0306");
    }
}
