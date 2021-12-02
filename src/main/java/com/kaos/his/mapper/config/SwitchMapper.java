package com.kaos.his.mapper.config;

import com.kaos.his.entity.config.Switch;

import org.springframework.stereotype.Repository;

@Repository
public interface SwitchMapper {
    /**
     * 查询开关
     * 
     * @param switchName 开关名
     * @param validCheck 是否检查有效性
     * @return
     */
    Switch QuerySwitch(String switchName, Boolean validCheck);
}
