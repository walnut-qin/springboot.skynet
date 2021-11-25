package com.kaos.his.enums;

import com.kaos.his.enums.util.IEnum;

public enum DrugValidStateEnum implements IEnum<DrugValidStateEnum> {
    停用("0", "停用"), 在用("1", "在用"), 废弃("2", "废弃");

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
    DrugValidStateEnum(String index, String description) {
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
