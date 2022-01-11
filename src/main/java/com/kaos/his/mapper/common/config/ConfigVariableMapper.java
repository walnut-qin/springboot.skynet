package com.kaos.his.mapper.common.config;

import java.util.List;

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

    /**
     * 查询参数列表
     * 
     * @param name
     * @param valid
     * @return
     */
    List<ConfigVariable> queryConfigVariableList(String name, ValidStateEnum valid);
}
