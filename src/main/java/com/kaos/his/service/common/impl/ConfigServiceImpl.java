package com.kaos.his.service.common.impl;

import com.kaos.his.entity.common.config.ConfigSwitch;
import com.kaos.his.service.common.ConfigService;
import com.kaos.inf.ICache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {
    /**
     * 开关cache
     */
    @Autowired
    ICache<ConfigSwitch> switchCache;

    @Override
    public Boolean querySwitchValue(String key) {
        var etty = this.switchCache.getValue(key);
        if (etty == null) {
            return false;
        } else {
            return etty.value;
        }
    }
}
