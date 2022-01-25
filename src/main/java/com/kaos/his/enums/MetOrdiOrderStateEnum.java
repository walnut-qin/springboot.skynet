package com.kaos.his.enums;

import com.kaos.inf.IEnum;

public enum MetOrdiOrderStateEnum implements IEnum {
    待审核("10", "待审核"), 暂存("15", "暂存"), 签发("20", "已提交(签发)"), 已接收("30", "已接收"), 已执行("40", "已执行"),
    已完成("50", "已完成"), 停止作废("90", "停止作废");

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
    MetOrdiOrderStateEnum(String value, String description) {
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
