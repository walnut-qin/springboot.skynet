package com.kaos.skynet.api.data.cache.pipe.lis;

import com.google.common.collect.Lists;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LisResultNewCacheTests {
    @Autowired
    LisResultNewCache lisResultNewCache;

    @Test
    void get() {
        lisResultNewCache.get(LisResultNewCache.Key.builder()
            .patientId("0001545200")
            .itemCodes(Lists.newArrayList("LYMPH#"))
            .offset(14).build());
    }
}
