package com.kaos.skynet.api.data.entity.common.config;

import com.kaos.skynet.api.data.enums.ValidEnum;

import lombok.Data;

@Data
public class ConfigSwitch {
    /**
     * 开关名
     */
    private String name = null;

    /**
     * 开关值
     */
    private Boolean value = null;

    /**
     * 有效标识
     */
    private ValidEnum valid = null;

    /**
     * 备注
     */
    private String remark = null;
}
