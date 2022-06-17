package com.kaos.skynet.api.data.his.mapper.common.config;

import com.kaos.skynet.api.data.his.entity.common.config.ConfigMap;

public interface ConfigMapMapper {
    /**
     * 主键查询
     * 
     * @param name 变量名；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    ConfigMap queryConfigMap(String name);
}
