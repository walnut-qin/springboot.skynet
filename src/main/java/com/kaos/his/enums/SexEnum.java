package com.kaos.his.enums;

import com.kaos.his.enums.util.IEnum;

public enum SexEnum implements IEnum {
    Male("M", "男"), Female("F", "女");

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
    SexEnum(String index, String description) {
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
