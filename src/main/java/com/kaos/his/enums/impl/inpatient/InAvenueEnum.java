package com.kaos.his.enums.impl.inpatient;

import com.kaos.his.enums.Enum;

public enum InAvenueEnum implements Enum {
    本市("1", "本市"), 市郊("2", "市郊"), 市外("3", "市外"), 省内("4", "省内"), 省外("5", "省外"), 境外("6", "境外");

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
    InAvenueEnum(String value, String description) {
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
