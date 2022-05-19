package com.kaos.skynet.api.enums.common;

import com.kaos.skynet.core.type.Enum;

/**
 * 药品/非药品 类别 {@code COM_DICTIONARY#TYPE = ITEMGRADE}
 */
public enum ItemGradeEnum implements Enum {
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
