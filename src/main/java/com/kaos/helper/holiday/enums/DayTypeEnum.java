package com.kaos.helper.holiday.enums;

import com.kaos.inf.IEnum;

public enum DayTypeEnum implements IEnum {
    工作日("0", "工作日"), 周末("-1", "周末"), 节日("2", "节日"), 调休("3", "调休");

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
    DayTypeEnum(String value, String description) {
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
