package com.kaos.skynet.enums.impl.inpatient.fee;

import com.kaos.skynet.core.type.Enum;

public enum PrepayStateEnum implements Enum {
    收取("0", "收取"),
    作废("1", "作废"),
    补打("2", "补打"),
    结算召回作废("3", "结算召回作废");

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
    PrepayStateEnum(String index, String description) {
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
