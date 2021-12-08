package com.kaos.his.mapper.config;

import com.kaos.his.entity.config.VariableList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VariableListMapper {
    /**
     * 查询列表
     * 
     * @param switchName 变量名
     * @param validCheck 是否检查有效性
     * @return
     */
    VariableList QueryVariableList(@Param("listName") String listName);
}
