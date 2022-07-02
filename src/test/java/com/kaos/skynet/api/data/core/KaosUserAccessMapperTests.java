package com.kaos.skynet.api.data.core;

import com.kaos.skynet.core.api.data.mapper.KaosUserAccessMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KaosUserAccessMapperTests {
    @Autowired
    KaosUserAccessMapper kaosUserAccessMapper;

    @Test
    void queryKaosUserAccess() {
        kaosUserAccessMapper.queryKaosUserAccess("admin");
    }
}
