package com.kaos.skynet.api.data.entity.common.config;

import com.kaos.skynet.api.data.enums.ValidEnum;

import lombok.Data;

@Data
public class ConfigMultiMap {
    /**
     * 开关名
     */
    private String name = null;

    /**
     * 开关值
     */
    private String value = null;

    /**
     * 有效标识
     */
    private ValidEnum valid = null;

    /**
     * 备注
     */
    private String remark = null;
}
