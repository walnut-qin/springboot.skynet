package com.kaos.skynet.api.mapper.common.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigMapMapperTests {
    @Autowired
    ConfigMapMapper configMapMapper;

    @Test
    public void queryMapValue() {
        this.configMapMapper.queryMapValue(null);
        this.configMapMapper.queryMapValue("name");
    }

    @Test
    public void queryMultiMapItemValue() {
        this.configMapMapper.queryMultiMapItemValue("name", null);
        this.configMapMapper.queryMultiMapItemValue("name", "value");
    }

    @Test
    public void queryMultiMapValues() {
        this.configMapMapper.queryMultiMapValues(null);
        this.configMapMapper.queryMultiMapValues("name");
    }
}
