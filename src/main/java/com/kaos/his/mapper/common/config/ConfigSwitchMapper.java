package com.kaos.his.mapper.common.config;

import com.kaos.his.entity.common.config.ConfigSwitch;
import com.kaos.his.enums.ValidStateEnum;

public interface ConfigSwitchMapper {
    /**
     * 主键查询
     * 
     * @param name
     * @param valid
     * @return
     */
    ConfigSwitch queryConfigSwitch(String name, ValidStateEnum valid);
}
