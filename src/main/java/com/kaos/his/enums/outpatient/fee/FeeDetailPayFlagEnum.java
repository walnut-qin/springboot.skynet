package com.kaos.his.enums.outpatient.fee;

import com.kaos.inf.IEnum;

public enum FeeDetailPayFlagEnum implements IEnum {
    划价("0", "划价"), 收费("1", "收费"), 预收费团体体检("3", "预收费团体体检"), 药品预审核("4", "药品预审核");

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
    FeeDetailPayFlagEnum(String value, String description) {
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
