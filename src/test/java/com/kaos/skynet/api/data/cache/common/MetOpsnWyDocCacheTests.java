package com.kaos.skynet.api.data.cache.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsnWyDocCacheTests {
    @Autowired
    MetOpsnWyDocCache metOpsnWyDocCache;

    @Test
    void get() {
        metOpsnWyDocCache.get("W00014");
    }
}
