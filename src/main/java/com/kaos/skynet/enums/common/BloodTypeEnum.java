package com.kaos.skynet.enums.common;

import com.kaos.skynet.core.type.Enum;

public enum BloodTypeEnum implements Enum {
    A("01", "A"), B("02", "B"), O("03", "O"), AB("04", "AB");

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
    BloodTypeEnum(String index, String description) {
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
