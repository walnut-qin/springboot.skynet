package com.kaos.skynet.api.cache.pipe.pacs;

import com.kaos.skynet.api.cache.Cache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.image.BufferedImage;

@SpringBootTest
public class PacsImageCacheTests {
    @Autowired
    Cache<String, BufferedImage> pacsImageCache;

    @Test
    public void getValue() {
        this.pacsImageCache.getValue("122222");
    }
}
