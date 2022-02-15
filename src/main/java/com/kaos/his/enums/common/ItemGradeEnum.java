package com.kaos.his.enums.common;

import com.kaos.inf.IEnum;

/**
 * 甲乙丙类标记
 */
public enum ItemGradeEnum implements IEnum {
    甲("1", "甲类"), 乙("2", "乙类"), 丙("3", "丙类");

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
    ItemGradeEnum(String index, String description) {
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
