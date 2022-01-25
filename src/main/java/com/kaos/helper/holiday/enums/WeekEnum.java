package com.kaos.helper.holiday.enums;

import com.kaos.inf.IEnum;

/**
 * 星期枚举
 */
public enum WeekEnum implements IEnum {
    Monday("1", "周一"), Tuesday("2", "周二"), Wednesday("3", "周三"), Thursday("4", "周四"), Friday("5", "周五"),
    Saturday("6", "周六"), Sunday("7", "周日");

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
    WeekEnum(String value, String description) {
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
