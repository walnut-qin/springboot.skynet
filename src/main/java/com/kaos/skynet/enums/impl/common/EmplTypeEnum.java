package com.kaos.skynet.enums.impl.common;

import com.kaos.skynet.core.type.Enum;

public enum EmplTypeEnum implements Enum {
    所有("A", "所有"),
    厨师("C", "厨师"),
    医生("D", "医生"),
    收款员("F", "收款员"),
    护士("N", "护士"),
    其他("O", "其他"),
    药师("P", "药师"),
    技师("T", "技师");

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
    EmplTypeEnum(String index, String description) {
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
