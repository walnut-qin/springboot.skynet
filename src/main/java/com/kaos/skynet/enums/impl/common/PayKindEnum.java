package com.kaos.skynet.enums.impl.common;

import com.kaos.skynet.enums.Enum;

/**
 * 结算类别代码 {@code COM_DICTIONARY#TYPE = PAYKIND}
 */
public enum PayKindEnum implements Enum {
    自费("01", "自费"), 医保("02", "医保"), 公费("03", "公费"), 特约单位("04", "特约单位"), 本院职工("05", "本院职工");

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
    PayKindEnum(String value, String description) {
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
