package com.kaos.his.enums;

import com.kaos.inf.IEnum;

/**
 * 枚举：入院来源
 */
public enum InpatientSourceEnum implements IEnum {
    门诊("1", "门诊"), 急诊("2", "急诊"), 转科("3", "转科"), 转院("4", "转院");

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
    InpatientSourceEnum(String value, String description) {
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
