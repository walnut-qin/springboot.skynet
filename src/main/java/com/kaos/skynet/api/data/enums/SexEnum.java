package com.kaos.skynet.api.data.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SexEnum implements Enum {
    Male("M", "男"),
    Female("F", "女"),
    Unknown("U", "未知");

    /**
     * 数据库存值
     */
    @Getter
    private String value;

    /**
     * 描述存值
     */
    @Getter
    private String description;
}
