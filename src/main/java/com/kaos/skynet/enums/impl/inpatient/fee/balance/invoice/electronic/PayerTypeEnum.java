package com.kaos.skynet.enums.impl.inpatient.fee.balance.invoice.electronic;

import com.kaos.skynet.core.type.Enum;

public enum PayerTypeEnum implements Enum {
    个人("1", "个人"),
    单位("2", "单位");

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
    PayerTypeEnum(String index, String description) {
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
