package com.kaos.skynet.api.mapper.common.config;

import java.util.List;

import com.kaos.skynet.api.entity.common.config.ConfigMap;

import org.springframework.stereotype.Repository;

@Repository
public interface ConfigMapMapper {
    /**
     * 主键查询
     * 
     * @param name 变量名；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    ConfigMap queryMapValue(String name);

    /**
     * 查询参数列表
     * 
     * @param name  变量名；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param value 变量值；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    ConfigMap queryMultiMapItemValue(String name, String value);

    /**
     * 查询参数列表
     * 
     * @param name 变量名；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    List<ConfigMap> queryMultiMapValues(String name);
}
