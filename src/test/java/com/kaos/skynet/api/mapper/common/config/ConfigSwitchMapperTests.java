package com.kaos.skynet.api.mapper.common.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigSwitchMapperTests {
    @Autowired
    ConfigSwitchMapper configSwitchMapper;

    @Test
    public void queryConfigSwitch() {
        this.configSwitchMapper.queryConfigSwitch(null);
        this.configSwitchMapper.queryConfigSwitch("var1");
    }
}
