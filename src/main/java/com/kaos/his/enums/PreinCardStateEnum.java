package com.kaos.his.enums;

public enum PreinCardStateEnum implements IEnum {
    预约("0", "预约"), 作废("1", "作废"), 转住院("2", "转住院"), 签床("3", "签床"), 预住院预约("4", "预住院预约");

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
    PreinCardStateEnum(String value, String description) {
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
