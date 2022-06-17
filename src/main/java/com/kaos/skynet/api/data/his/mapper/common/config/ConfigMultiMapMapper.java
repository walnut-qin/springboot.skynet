package com.kaos.skynet.api.data.his.mapper.common.config;

import java.util.List;

import com.kaos.skynet.api.data.his.entity.common.config.ConfigMultiMap;

public interface ConfigMultiMapMapper {
    /**
     * 主键查询
     * 
     * @param name  变量名；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param value 变量值；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    ConfigMultiMap queryConfigMultiMap(String name, String value);

    /**
     * 列表查询
     * 
     * @param name 变量名；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    List<ConfigMultiMap> queryConfigMultiMaps(String name);
}
