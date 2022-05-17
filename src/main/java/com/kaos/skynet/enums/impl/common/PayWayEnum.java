package com.kaos.skynet.enums.impl.common;

import com.kaos.skynet.core.type.Enum;

/**
 * 付款方式
 */
public enum PayWayEnum implements Enum {
    现金("CA", "现金"),
    支票("CH", "支票"),
    信用卡("CD", "信用卡"),
    借记卡("DB", "借记卡"),
    转押金("AJ", "转押金"),
    汇票("PO", "汇票"),
    保险帐户("PS", "保险帐户"),
    院内账户("YS", "院内账户"),
    东风卡("DFK", "东风卡"),
    日间手术账户("RJSH", "日间手术账户");

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
    PayWayEnum(String value, String description) {
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
