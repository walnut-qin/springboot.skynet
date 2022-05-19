package com.kaos.skynet.api.enums.common;

import com.kaos.skynet.core.type.Enum;

public enum SourceTypeEnum implements Enum {
    窗口("1", "窗口"),
    自助机("2", "自助机"),
    微信("3", "微信"),
    支付宝("4", "支付宝");

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
    SourceTypeEnum(String index, String description) {
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
