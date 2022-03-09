package com.kaos.skynet.api.mapper.pipe.pacs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PacsCropImageRecMapperTests {
    @Autowired
    PacsCropImageRecMapper cropImageRecMapper;

    @Test
    public void queryImage() {
        this.cropImageRecMapper.queryRec(null);
        this.cropImageRecMapper.queryRec("123");
    }
}
