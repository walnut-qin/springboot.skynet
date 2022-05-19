package com.kaos.skynet.api.cache.pipe.pacs;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.entity.pipe.pacs.PacsCropImageRec;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PacsCropImageRecCacheTests {
    @Autowired
    Cache<String, PacsCropImageRec> pacsCropImageRecCache;

    @Test
    public void getValue() {
        this.pacsCropImageRecCache.getValue("122222");
    }
}
