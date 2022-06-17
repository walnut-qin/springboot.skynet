package com.kaos.skynet.api.data.his.cache.inpatient.escort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortVipCacheTests {
    @Autowired
    EscortVipCache vipCache;

    @Test
    void get() {
        vipCache.get(EscortVipCache.Key.builder().cardNo("2000003605").happenNo(10).build());
    }
}
