package com.kaos.skynet.api.data.cache.common.config;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.common.config.ConfigMap;
import com.kaos.skynet.api.data.mapper.common.config.ConfigMapMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConfigMapCache extends Cache<String, ConfigMap> {
    @Autowired
    ConfigMapMapper configMapMapper;

    @Override
    @PostConstruct
    protected void postConstruct() {
        super.postConstruct(String.class, 100, new Converter<String, ConfigMap>() {
            @Override
            public ConfigMap convert(String source) {
                return configMapMapper.queryConfigMap(source);
            }
        });
    }
}
