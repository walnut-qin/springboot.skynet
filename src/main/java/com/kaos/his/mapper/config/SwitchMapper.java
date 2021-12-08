package com.kaos.his.mapper.config;

import com.kaos.his.entity.config.Switch;

import org.apache.ibatis.annotations.Param;
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
    Switch QuerySwitch(@Param("switchName") String switchName);

    /**
     * 启动开关
     * 
     * @param switchName
     */
    void TurnOn(@Param("switchName") String switchName);

    /**
     * 关闭开关
     * 
     * @param switchName
     */
    void TurnOff(@Param("switchName") String switchName);
}
