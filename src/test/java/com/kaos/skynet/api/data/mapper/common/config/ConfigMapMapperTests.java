package com.kaos.skynet.api.data.mapper.common.config;

import com.kaos.skynet.api.data.entity.common.config.ConfigMap;

import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigMapMapperTests {
    @Autowired
    ConfigMapMapper configMapMapper;

    @Test
    void queryConfigMap() {
        ConfigMap configMap = this.configMapMapper.queryConfigMap(null);
        if (configMap != null) {
            throw new TestAbortedException("查询内容异常");
        }
    }
}
