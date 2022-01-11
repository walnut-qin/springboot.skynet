package com.kaos.his.entity.common.config;

import com.kaos.his.enums.ValidStateEnum;

/**
 * 控制变量（KAOS.CONFIG_VARIABLE）
 */
public class ConfigVariable {
    /**
     * 开关名
     */
    public String name = null;

    /**
     * 开关值
     */
    public String value = null;

    /**
     * 有效标识
     */
    public ValidStateEnum valid = null;

    /**
     * 备注
     */
    public String remark = null;
}
