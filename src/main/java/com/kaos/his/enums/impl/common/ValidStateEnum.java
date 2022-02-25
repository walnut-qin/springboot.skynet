package com.kaos.his.enums.common;

import com.kaos.his.enums.IEnum;

public enum ValidStateEnum implements IEnum {
    无效("0", "无效"), 有效("1", "有效"), 作废("2", "作废");

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
    ValidStateEnum(String index, String description) {
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
