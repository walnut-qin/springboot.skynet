package com.kaos.skynet.api.enums.common;

import com.kaos.skynet.core.type.Enum;

public enum TravelCodeEnum implements Enum {
    正常("0", "正常"), 带星号("1", "带星号"), 黄码("2", "黄码"), 红码("3", "红码");

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
    TravelCodeEnum(String index, String description) {
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
