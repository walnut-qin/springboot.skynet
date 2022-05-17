package com.kaos.skynet.enums.impl.common;

import com.kaos.skynet.core.type.Enum;

public enum TradeCodeEnum implements Enum {
    正交易("S1", "正交易"),
    当日反交易("S2", "当日反交易"),
    隔日反交易("07", "隔日退费");

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
    TradeCodeEnum(String index, String description) {
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
