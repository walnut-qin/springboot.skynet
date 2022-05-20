package com.kaos.skynet.api.data.mapper.common.config;

import com.kaos.skynet.api.data.entity.common.config.ConfigSwitch;

public interface ConfigSwitchMapper {
    /**
     * 主键查询
     * 
     * @param name 变量名；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    ConfigSwitch queryConfigSwitch(String name);
}
