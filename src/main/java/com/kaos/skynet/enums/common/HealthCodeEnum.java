package com.kaos.skynet.enums.common;

import com.kaos.skynet.core.type.Enum;

public enum HealthCodeEnum implements Enum {
    绿码("0", "绿码"), 黄码("1", "黄码"), 红码("2", "红码");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;

    /**
     * 构造
     * 
     * @param index
     * @param description
     */
    HealthCodeEnum(String index, String description) {
        this.value = index;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
