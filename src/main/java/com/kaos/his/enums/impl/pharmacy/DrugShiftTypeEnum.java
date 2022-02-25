package com.kaos.his.enums.impl.pharmacy;

import com.kaos.his.enums.Enum;

public enum DrugShiftTypeEnum implements Enum {
    更新("U", "更新"), 特殊修改("M", "特殊修改"), 新药("N", "新药"), 停用("S", "停用"), 调价("A", "调价");

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
    DrugShiftTypeEnum(String index, String description) {
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
