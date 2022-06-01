package com.kaos.skynet.api.data.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BloodTypeEnum implements Enum {
    A("01", "A"),
    B("02", "B"),
    O("03", "O"),
    AB("04", "AB");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
