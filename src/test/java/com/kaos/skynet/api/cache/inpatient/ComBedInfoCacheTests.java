package com.kaos.skynet.api.cache.inpatient;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.data.entity.inpatient.ComBedInfo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComBedInfoCacheTests {
    @Autowired
    Cache<String, ComBedInfo> cache;

    @Test
    public void getCacheValue() {
        this.cache.getValue("901985");
    }
}
