package com.kaos.skynet.api.data.cache.inpatient.escort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortMainInfoCacheTests {
    @Autowired
    EscortMainInfoCache mainInfoCache;

    @Test
    void get() {
        mainInfoCache.get("0000001094");
    }
}
