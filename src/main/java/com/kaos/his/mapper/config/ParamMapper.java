package com.kaos.his.mapper.config;

import com.kaos.his.entity.config.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface ParamMapper {
    /**
     * 查询变量
     * 
     * @param paramName 变量名
     * @param validCheck 是否检查有效性
     * @return
     */
    Param QueryParam(String paramName, Boolean validCheck);
}
