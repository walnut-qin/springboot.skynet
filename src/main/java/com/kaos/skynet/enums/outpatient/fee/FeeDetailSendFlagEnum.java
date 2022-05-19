package com.kaos.skynet.enums.outpatient.fee;

import com.kaos.skynet.core.type.Enum;

public enum FeeDetailSendFlagEnum implements Enum {
    未转("0", "未转"), 已转("1", "已转"), 转失败("2", "转失败");

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
    FeeDetailSendFlagEnum(String value, String description) {
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
