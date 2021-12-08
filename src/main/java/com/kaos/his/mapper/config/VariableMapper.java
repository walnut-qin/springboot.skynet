package com.kaos.his.mapper.config;

import com.kaos.his.entity.config.Variable;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface VariableMapper {
    /**
     * 查询变量
     * 
     * @param paramName  变量名
     * @param validCheck 是否检查有效性
     * @return
     */
    Variable QueryVariable(@Param("varName") String varName);
}
