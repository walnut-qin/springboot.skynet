package com.kaos.skynet.api.enums.inpatient.fee.balance.invoice.electronic;

import com.kaos.skynet.core.type.Enum;

/**
 * 电子票据状态专用枚举
 */
public enum StateEnum implements Enum {
    有效("1", "有效"), 冲红("2", "冲红");

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
    StateEnum(String index, String description) {
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
