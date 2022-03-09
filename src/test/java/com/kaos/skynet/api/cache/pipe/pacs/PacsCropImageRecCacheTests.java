package com.kaos.skynet.api.cache.pipe.pacs;

import com.kaos.skynet.api.cache.Cache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.image.BufferedImage;

@SpringBootTest
public class PacsCropImageRecCacheTests {
    @Autowired
    Cache<String, BufferedImage> cache;

    @Test
    public void getValue() {
        this.cache.getValue("122222");
    }
}
