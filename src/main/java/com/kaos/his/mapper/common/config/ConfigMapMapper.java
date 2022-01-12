package com.kaos.his.mapper.common.config;

import java.util.List;

import com.kaos.his.entity.common.config.ConfigMap;

import org.springframework.stereotype.Repository;

@Repository
public interface ConfigMapMapper {
    /**
     * 主键查询
     * 
     * @param name
     * @return
     */
    ConfigMap queryMapValue(String name);

    /**
     * 查询参数列表
     * 
     * @param name
     * @param valid
     * @return
     */
    ConfigMap queryMultiMapItemValue(String name, String value);

    /**
     * 查询参数列表
     * 
     * @param name
     * @param valid
     * @return
     */
    List<ConfigMap> queryMultiMapValues(String name);
}
