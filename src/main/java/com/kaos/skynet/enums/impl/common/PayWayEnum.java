package com.kaos.skynet.enums.impl.common;

import com.kaos.skynet.enums.Enum;

/**
 * 付款方式
 */
public enum PayWayEnum implements Enum {
    CA("CA", "现金");

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
    PayWayEnum(String value, String description) {
        this.value = value;
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
