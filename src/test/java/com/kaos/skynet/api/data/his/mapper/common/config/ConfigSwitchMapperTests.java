package com.kaos.skynet.api.data.his.mapper.common.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigSwitchMapperTests {
    @Autowired
    ConfigSwitchMapper configSwitchMapper;

    @Test
    void queryConfigSwitch() {
        configSwitchMapper.queryConfigSwitch("switch");
    }
}
