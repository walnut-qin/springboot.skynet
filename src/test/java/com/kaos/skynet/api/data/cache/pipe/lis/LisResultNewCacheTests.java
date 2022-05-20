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
        lisResultNewCache.get(new LisResultNewCache.Key() {
            {
                setPatientId("0001545200");
                setItemCodes(Lists.newArrayList("LYMPH#"));
                setOffset(-14);
            }
        });
    }
}
