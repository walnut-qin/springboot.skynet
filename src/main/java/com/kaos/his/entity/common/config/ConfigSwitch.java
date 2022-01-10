package com.kaos.his.entity.common.config;

import com.kaos.his.enums.ValidStateEnum;

/**
 * 开关控制器（KAOS.CONFIG_SWITCHS）
 */
public class ConfigSwitch {
    /**
     * 开关名
     */
    public String name = null;

    /**
     * 开关值
     */
    public Boolean value = null;

    /**
     * 有效标识
     */
    public ValidStateEnum valid = null;

    /**
     * 备注
     */
    public String remark = null;
}
