package com.kaos.skynet.api.data.cache.inpatient.escort.annex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortAnnexInfoCacheTests {
    @Autowired
    EscortAnnexInfoCache.MasterCache annexInfoMasterCache = null;

    @Autowired
    EscortAnnexInfoCache.SlaveCache annexInfoSlaveCache = null;

    @Test
    void get() {
        annexInfoMasterCache.get("0000000001");
        var a = EscortAnnexInfoCache.SlaveCacheKey.builder()
                .cardNo("2552076744").build();
        annexInfoSlaveCache.get(a);
    }
}
