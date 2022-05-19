package com.kaos.skynet.api.entity.common.config;

import com.kaos.skynet.enums.common.ValidStateEnum;

/**
 * 开关控制器（KAOS.CONFIG_SWITCH）
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
