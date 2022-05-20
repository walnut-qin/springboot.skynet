package com.kaos.skynet.api.data.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ValidEnum implements Enum {
    INVALID("0", "无效"),
    VALID("1", "有效");

    @Getter
    private String value;

    @Getter
    private String description;
}
