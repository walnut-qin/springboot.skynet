package com.kaos.his.mapper.common.config;

import com.kaos.his.entity.common.config.ConfigVariable;
import com.kaos.his.enums.ValidStateEnum;

import org.springframework.stereotype.Repository;

@Repository
public interface ConfigVariableMapper {
    /**
     * 主键查询
     * 
     * @param name
     * @return
     */
    ConfigVariable queryConfigVariable(String name, ValidStateEnum valid);
}
