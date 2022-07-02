package com.kaos.skynet.api.data.core;

import com.kaos.skynet.core.api.data.mapper.KaosUserRoleMapper;
import com.kaos.skynet.core.api.data.mapper.KaosUserRoleMapper.Key;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KaosUserRoleMapperTests {
    @Autowired
    KaosUserRoleMapper kaosUserRoleMapper;

    @Test
    void queryKaosUserRole() {
        kaosUserRoleMapper.queryKaosUserRole("admin", "admin");
    }

    @Test
    void queryKaosUserRoles() {
        kaosUserRoleMapper.queryKaosUserRoles(Key.builder().userCode("admin").build());
    }
}
