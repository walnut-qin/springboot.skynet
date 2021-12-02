package com.kaos.his.mapper.config;

import com.kaos.his.entity.config.ParamList;

import org.springframework.stereotype.Repository;

@Repository
public interface ParamListMapper {
    /**
     * 查询列表
     * 
     * @param switchName 变量名
     * @param validCheck 是否检查有效性
     * @return
     */
    ParamList QueryParamList(String listName, Boolean validCheck);
}
