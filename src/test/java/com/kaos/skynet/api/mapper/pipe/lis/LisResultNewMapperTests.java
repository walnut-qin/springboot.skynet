package com.kaos.skynet.api.mapper.pipe.lis;

import java.util.Date;

import com.google.common.collect.Lists;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LisResultNewMapperTests {
    @Autowired
    LisResultNewMapper lisResultNewMapper;

    @Test
    public void queryInspectResult() {
        this.lisResultNewMapper.queryInspectResult("0001545200", Lists.newArrayList("LYMPH#"), new Date(), new Date());
    }
}
