package com.kaos.skynet.api.data.his.cache.inpatient.escort.annex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortAnnexInfoCacheTests {
    @Autowired
    EscortAnnexInfoCache annexInfoCache = null;

    @Test
    void get() {
        annexInfoCache.get("0000000001");
    }
}
