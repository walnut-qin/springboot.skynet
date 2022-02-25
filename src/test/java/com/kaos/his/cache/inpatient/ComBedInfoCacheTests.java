package com.kaos.his.cache.inpatient;

import com.kaos.his.cache.Cache;
import com.kaos.his.entity.inpatient.ComBedInfo;

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
