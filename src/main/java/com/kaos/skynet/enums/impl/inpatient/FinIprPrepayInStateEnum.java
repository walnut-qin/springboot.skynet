package com.kaos.skynet.enums.impl.inpatient;

import com.kaos.skynet.core.type.Enum;

public enum FinIprPrepayInStateEnum implements Enum {
    预约("0", "预约"), 作废("1", "作废"), 转住院("2", "转住院"), 签床("3", "签床"), 预住院预约("4", "预住院预约");

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
    FinIprPrepayInStateEnum(String value, String description) {
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
