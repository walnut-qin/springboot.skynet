package com.kaos.his.enums.impl.outpatient.fee;

import com.kaos.his.enums.Enum;

public enum FeeDetailCostSourceEnum implements Enum {
    操作员("0", "操作员"), 医嘱("1", "医嘱"), 终端("2", "终端"), 体检("3", "体检");

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
    FeeDetailCostSourceEnum(String value, String description) {
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
