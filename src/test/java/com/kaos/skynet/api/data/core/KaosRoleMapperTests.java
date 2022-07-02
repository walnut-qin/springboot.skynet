package com.kaos.skynet.api.data.core;

import com.kaos.skynet.core.api.data.mapper.KaosRoleMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KaosRoleMapperTests {
    @Autowired
    KaosRoleMapper kaosRoleMapper;

    @Test
    void queryKaosRole() {
        kaosRoleMapper.queryKaosRole("admin");
    }
}
