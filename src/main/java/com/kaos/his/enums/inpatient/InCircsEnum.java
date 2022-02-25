package com.kaos.his.enums.inpatient;

import com.kaos.his.enums.IEnum;

/**
 * 入院情况 {@code COM_DICTIONARY#TYPE = INCIRCS}
 */
public enum InCircsEnum implements IEnum {
    一般("1", "一般"), 急("2", "急"), 危("3", "危");

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
    InCircsEnum(String value, String description) {
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
