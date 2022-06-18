package com.kaos.skynet.api.data.his.cache.common.config;

import com.kaos.skynet.api.data.his.entity.common.config.ConfigMap;
import com.kaos.skynet.api.data.his.mapper.common.config.ConfigMapMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConfigMapCache extends Cache<String, ConfigMap> {
    ConfigMapCache(ConfigMapMapper configMapMapper) {
        super(100, new Converter<String, ConfigMap>() {
            @Override
            public ConfigMap convert(String source) {
                return configMapMapper.queryConfigMap(source);
            }
        });
    }
}
