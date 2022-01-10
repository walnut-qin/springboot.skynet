package com.kaos.his.common.config;

import com.kaos.his.enums.ValidStateEnum;
import com.kaos.his.mapper.common.config.ConfigSwitchMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigSwitchMapperTests {
    @Autowired
    ConfigSwitchMapper configSwitchMapper;

    @Test
    public void queryConfigSwitch() {
        this.configSwitchMapper.queryConfigSwitch("switch", null);
        this.configSwitchMapper.queryConfigSwitch("switch", ValidStateEnum.有效);
    }
}
