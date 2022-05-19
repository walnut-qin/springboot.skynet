package com.kaos.skynet.enums.inpatient.fee.balance.invoice.electronic;

import com.kaos.skynet.core.type.Enum;

public enum PlaceCodeEnum implements Enum {
    南院区("001", "南院区"),
    北院区("002", "北院区"),
    东津院区("003", "东津院区");

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
    PlaceCodeEnum(String index, String description) {
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
