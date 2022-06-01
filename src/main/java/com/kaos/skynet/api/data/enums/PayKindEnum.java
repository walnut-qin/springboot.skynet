package com.kaos.skynet.api.data.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结算类别代码 {@code COM_DICTIONARY#TYPE = PAYKIND}
 */
@Getter
@AllArgsConstructor
public enum PayKindEnum implements Enum {
    自费("01", "自费"), 医保("02", "医保"), 公费("03", "公费"), 特约单位("04", "特约单位"), 本院职工("05", "本院职工");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
