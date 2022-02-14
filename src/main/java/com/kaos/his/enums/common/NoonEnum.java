package com.kaos.his.enums.common;

import com.kaos.inf.IEnum;

/**
 * 午别
 */
public enum NoonEnum implements IEnum {
    上午("1", "上午"), 下午("2", "下午"), 夜班("3", "夜班"), 中班("4", "中班");

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
    NoonEnum(String value, String description) {
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
