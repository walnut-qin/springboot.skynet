package com.kaos.his.enums.inpatient.fee.balance;

import com.kaos.his.enums.IEnum;

public enum BalancePayTransKindEnum implements IEnum {
    预交款("0", "预交款"), 结算款("1", "结算款");

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
    BalancePayTransKindEnum(String index, String description) {
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
