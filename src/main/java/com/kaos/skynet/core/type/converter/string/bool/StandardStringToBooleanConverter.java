package com.kaos.skynet.core.type.converter.string.bool;

import org.springframework.stereotype.Component;

@Component
public class StandardStringToBooleanConverter extends AbstractStringToBooleanConverter {
    /**
     * 构造函数
     */
    public StandardStringToBooleanConverter() {
        super("true", "false");
    }
}
