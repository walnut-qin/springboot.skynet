package com.kaos.skynet.core.type.converter.string.bool;

import org.springframework.stereotype.Component;

@Component("NumericStringToBooleanConverter")
public class NumericStringToBooleanConverter extends AbstractStringToBooleanConverter {
    /**
     * 构造函数
     */
    public NumericStringToBooleanConverter() {
        super("1", "0");
    }
}
