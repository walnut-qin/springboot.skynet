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
        annexInfoSlaveCache.get(new EscortAnnexInfoCache.SlaveCache.Key() {
            {
                setCardNo("0123456789");
                setChecked(false);
            }
        });
    }
}
