package com.kaos.his.common.config;

import com.kaos.his.mapper.common.config.ConfigMapMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigMapMapperTests {
    @Autowired
    ConfigMapMapper configMapMapper;

    @Test
    public void queryMapValue() {
        this.configMapMapper.queryMapValue("name");
    }

    @Test
    public void queryMultiMapItemValue() {
        this.configMapMapper.queryMultiMapItemValue("name", "value");
    }

    @Test
    public void queryMultiMapValues() {
        this.configMapMapper.queryMultiMapValues("name");
    }
}
