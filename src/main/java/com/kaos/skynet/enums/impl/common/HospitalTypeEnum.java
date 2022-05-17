package com.kaos.skynet.enums.impl.common;

import com.kaos.skynet.core.type.Enum;

public enum HospitalTypeEnum implements Enum {
    综合医院("0", "综合医院"),
    中医医院("1", "中医医院"),
    中西医结合医院("2", "中西医结合医院"),
    名族医医院("3", "名族医医院"),
    专科医院("4", "专科医院"),
    康复医院("5", "康复医院");

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
    HospitalTypeEnum(String index, String description) {
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
