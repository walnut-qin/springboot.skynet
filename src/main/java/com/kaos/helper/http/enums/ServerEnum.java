package com.kaos.helper.http.enums;

import com.kaos.inf.IEnum;

public enum ServerEnum implements IEnum {
    his("172.16.100.252", "8025"), docare("172.16.100.252", "8002"), proxy("172.16.100.252", "8003");

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
    ServerEnum(String index, String description) {
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
