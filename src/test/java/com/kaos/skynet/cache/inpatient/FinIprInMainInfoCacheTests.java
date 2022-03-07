package com.kaos.skynet.cache.inpatient;

import com.kaos.skynet.cache.Cache;
import com.kaos.skynet.entity.inpatient.FinIprInMainInfo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIprInMainInfoCacheTests {
    @Autowired
    Cache<String, FinIprInMainInfo> cache;

    @Test
    public void getCacheValue() {
        this.cache.getValue("ZY010000705856");
    }    
}
