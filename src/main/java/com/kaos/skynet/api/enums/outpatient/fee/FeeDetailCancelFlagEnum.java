package com.kaos.skynet.api.enums.outpatient.fee;

import com.kaos.skynet.core.type.Enum;

public enum FeeDetailCancelFlagEnum implements Enum {
    退费("0", "退费"), 正常("1", "正常"), 重打("2", "重打"), 注销("3", "注销");

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
    FeeDetailCancelFlagEnum(String value, String description) {
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
