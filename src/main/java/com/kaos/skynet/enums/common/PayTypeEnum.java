package com.kaos.skynet.enums.common;

import com.kaos.skynet.core.type.Enum;

public enum PayTypeEnum implements Enum {
    现金0("0", "现金"),
    就诊卡("1", "就诊卡"),
    银行卡("2", "银行卡"),
    支付宝("3", "支付宝"),
    微信("4", "微信"),
    现金5("5", "现金"),
    医保卡6("6", "医保卡"),
    医保卡7("7", "医保卡"),
    其他("9", "其他"),
    建行("10", "建行"),
    weimaiAPP("12", "weimaiAPP"),
    医保线上支付("13", "医保线上支付");

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
    PayTypeEnum(String value, String description) {
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
