package com.kaos.his.mapper.common.config;

import com.kaos.his.entity.common.config.DawnCodeType;
import com.kaos.his.enums.ValidStateEnum;

public interface DawnCodeTypeMapper {
    /**
     * 主键查询
     * 
     * @param constTypeId
     * @param valid
     * @return
     */
    DawnCodeType queryDawnCodeType(String constTypeId, ValidStateEnum valid);
}
