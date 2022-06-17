package com.kaos.skynet.api.data.his.mapper.common.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigMultiMapMapperTests {
    @Autowired
    ConfigMultiMapMapper configMultiMapMapper;

    @Test
    void queryConfigMultiMap() {
        configMultiMapMapper.queryConfigMultiMap(null, null);
    }

    @Test
    void queryConfigMultiMaps() {
        configMultiMapMapper.queryConfigMultiMaps(null);
    }
}
