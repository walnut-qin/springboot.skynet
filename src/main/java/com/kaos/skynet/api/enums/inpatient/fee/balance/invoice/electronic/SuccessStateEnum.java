package com.kaos.skynet.api.enums.inpatient.fee.balance.invoice.electronic;

import com.kaos.skynet.core.type.Enum;

/**
 * 操作状态
 */
public enum SuccessStateEnum implements Enum {
    失败("0", "失败"),
    成功("1", "成功"),
    补开成功("2", "补开成功"),
    补冲红成功("3", "补冲红成功");

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
    SuccessStateEnum(String index, String description) {
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
