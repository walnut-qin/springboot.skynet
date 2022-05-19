package com.kaos.skynet.api.enums.inpatient.escort;

import com.kaos.skynet.core.type.Enum;

public enum EscortActionEnum implements Enum {
    进入("I", "进入"), 外出("O", "外出");

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
    EscortActionEnum(String index, String description) {
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
