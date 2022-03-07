package com.kaos.skynet.mapper.pipe.lis;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LisResultNewMapperTests {
    @Autowired
    LisResultNewMapper lisResultNewMapper;

    @Test
    public void queryInspectResult() {
        this.lisResultNewMapper.queryInspectResult("0001545200", "LYMPH#", new Date(), new Date());
    }
}
