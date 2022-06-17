package com.kaos.skynet.api.data.his.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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
}
