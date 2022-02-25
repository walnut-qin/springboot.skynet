package com.kaos.his.service.impl.common.config;

import com.kaos.his.entity.common.config.ConfigSwitch;
import com.kaos.his.enums.common.ValidStateEnum;
import com.kaos.his.service.inf.common.config.SwitchService;
import com.kaos.inf.ICache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwitchServiceImpl implements SwitchService {
    /**
     * 开关cache
     */
    @Autowired
    ICache<String, ConfigSwitch> switchCache;

    @Override
    public Boolean querySwitchValue(String key) {
        var etty = this.switchCache.getValue(key);
        if (etty == null || etty.valid != ValidStateEnum.有效) {
            return false;
        } else {
            return etty.value;
        }
    }
}
