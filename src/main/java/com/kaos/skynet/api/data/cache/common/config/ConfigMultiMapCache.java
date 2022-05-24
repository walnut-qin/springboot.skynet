package com.kaos.skynet.api.data.cache.common.config;

import java.util.List;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.common.config.ConfigMultiMap;
import com.kaos.skynet.api.data.mapper.common.config.ConfigMultiMapMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;

@Component
public class ConfigMultiMapCache {
    @Autowired
    ConfigMultiMapMapper configMultiMapMapper;

    @Component
    public class MasterCache extends Cache<Key, ConfigMultiMap> {
        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(Key.class, 100, new Converter<Key, ConfigMultiMap>() {
                @Override
                public ConfigMultiMap convert(Key source) {
                    return configMultiMapMapper.queryConfigMultiMap(source.name, source.value);
                }
            });
        }
    }

    @Component
    public class SlaveCache extends Cache<String, List<ConfigMultiMap>> {
        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(String.class, 100, new Converter<String, List<ConfigMultiMap>>() {
                @Override
                public List<ConfigMultiMap> convert(String source) {
                    return configMultiMapMapper.queryConfigMultiMaps(source);
                }
            });
        }
    }

    @Data
    @Builder
    public static class Key {
        /**
         * 变量名
         */
        private String name;

        /**
         * 变量值
         */
        private String value;
    }
}
