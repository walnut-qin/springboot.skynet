package com.kaos.his.service.common;

public interface ConfigService {
    /**
     * 查询开关变量的值
     * 
     * @param key 开关名
     * @return
     */
    Boolean querySwitchValue(String key);
}
