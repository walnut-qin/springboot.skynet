package com.kaos.his.enums.inpatient.fee.balance;

import com.kaos.his.enums.IEnum;

/**
 * 结算标识
 */
public enum BalanceStateEnum implements IEnum {
    未结算("0", "未结算"), 已结算("1", "已结算"), 已结转("2", "已结转");

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
    BalanceStateEnum(String index, String description) {
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
