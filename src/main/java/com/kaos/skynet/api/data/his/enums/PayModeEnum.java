package com.kaos.skynet.api.data.his.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式
 */
@Getter
@AllArgsConstructor
public enum PayModeEnum implements Enum {
    账户余额("1", "账户余额"), 工行银行卡("2", "工行银行卡"), 支付宝("3", "支付宝"), 微信("4", "微信"), 现金("5", "现金"), 老医保("6", "老医保"),
    新医保("7", "新医保"), 诊间银行卡("8", "诊间银行卡"), 建行银行卡("10", "建行银行卡"), 扫码挂号("11", "扫码挂号");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
