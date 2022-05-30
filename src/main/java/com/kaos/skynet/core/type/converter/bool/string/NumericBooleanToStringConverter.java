package com.kaos.skynet.core.type.converter.bool.string;

import org.springframework.stereotype.Component;

@Component("NumericBooleanToStringConverter")
public class NumericBooleanToStringConverter extends AbstractBooleanToStringConverter {
    /**
     * 构造函数
     */
    public NumericBooleanToStringConverter() {
        super("1", "0");
    }
}
