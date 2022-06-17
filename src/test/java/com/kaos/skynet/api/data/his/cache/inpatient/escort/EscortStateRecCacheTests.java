package com.kaos.skynet.api.data.his.cache.inpatient.escort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortStateRecCacheTests {
    @Autowired
    EscortStateRecCache stateRecCache;

    @Test
    void get() {
        stateRecCache.get("0000001170");
    }
}
