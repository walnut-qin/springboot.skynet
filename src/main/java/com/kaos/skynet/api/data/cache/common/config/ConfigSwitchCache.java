package com.kaos.skynet.api.data.cache.common.config;

import com.kaos.skynet.api.data.entity.common.config.ConfigSwitch;
import com.kaos.skynet.api.data.mapper.common.config.ConfigSwitchMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConfigSwitchCache extends Cache<String, ConfigSwitch> {
    ConfigSwitchCache(ConfigSwitchMapper configSwitchMapper) {
        super(100, new Converter<String, ConfigSwitch>() {
            @Override
            public ConfigSwitch convert(String source) {
                return configSwitchMapper.queryConfigSwitch(source);
            }
        });
    }
}
