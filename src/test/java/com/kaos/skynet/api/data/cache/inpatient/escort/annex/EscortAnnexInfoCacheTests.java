package com.kaos.skynet.api.data.cache.inpatient.escort.annex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortAnnexInfoCacheTests {
    @Autowired
    EscortAnnexInfoCache annexInfoCache = null;

    @Test
    void get() {
        annexInfoCache.getMasterCache().get("0000000001");
        annexInfoCache.getSlaveCache().get(EscortAnnexInfoCache.SlaveCache.Key.builder().cardNo("2552076744").build());
    }
}
