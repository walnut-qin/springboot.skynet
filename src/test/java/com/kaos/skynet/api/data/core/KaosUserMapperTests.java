package com.kaos.skynet.api.data.core;

import com.kaos.skynet.core.api.data.mapper.KaosUserMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KaosUserMapperTests {
    @Autowired
    KaosUserMapper kaosUserMapper;

    @Test
    void queryKaosUser() {
        kaosUserMapper.queryKaosUser("000306");
    }
}
