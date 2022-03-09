package com.kaos.skynet.api.mapper.pipe.pacs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PacsCropImageRecMapperTests {
    @Autowired
    PacsCropImageRecMapper cropImageRecMapper;

    @Test
    public void queryRec() {
        this.cropImageRecMapper.queryRec("url", 0, 0, 10, 10);
    }

    @Test
    public void queryRecWithRefer() {
        this.cropImageRecMapper.queryRecWithRefer(null);
        this.cropImageRecMapper.queryRecWithRefer("123");
    }
}
