package com.kaos.his.service.inf.common.config;

public interface SwitchService {
    /**
     * 查询开关变量的值
     * 
     * @param key 开关名
     * @return
     */
    Boolean querySwitchValue(String key);
}
