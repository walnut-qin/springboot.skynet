package com.kaos.skynet.enums.impl.common;

import com.kaos.skynet.enums.Enum;

/**
 * 枚举：职务
 */
public enum PositionEnum implements Enum {
    院长("1", "院长"), 主任("2", "主任"), 科长("3", "科长"), 科员("4", "科员");

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
    PositionEnum(String value, String description) {
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
