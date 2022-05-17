package com.kaos.skynet.enums.impl.inpatient;

import com.kaos.skynet.core.type.Enum;

public enum BedStateEnum implements Enum {
    占床("O", "占床"), 空床("U", "空床"), 关闭("C", "关闭"), 挂床("H", "挂床"), 包床("W", "包床"), 污染("K", "污染"), 隔离("I", "隔离"),
    请假("R", "请假");

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
    BedStateEnum(String index, String description) {
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
