package com.kaos.his.mapper.config;

import com.kaos.his.entity.config.DawnCodeType;

import org.springframework.stereotype.Repository;

@Repository
public interface DawnCodeTypeMapper {
    /**
     * 查询有效记录
     * 
     * @param id
     * @return
     */
    DawnCodeType QueryValidCodeType(String id);
}
