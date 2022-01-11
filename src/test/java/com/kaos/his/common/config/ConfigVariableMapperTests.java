package com.kaos.his.common.config;

import com.kaos.his.enums.ValidStateEnum;
import com.kaos.his.mapper.common.config.ConfigVariableMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigVariableMapperTests {
    @Autowired
    ConfigVariableMapper configVariableMapper;

    @Test
    public void queryConfigVariable() {
        this.configVariableMapper.queryConfigVariable("EscortRegOffset", null);
        this.configVariableMapper.queryConfigVariable("EscortRegOffset", ValidStateEnum.有效);
    }
}
