package com.kaos.skynet.core.type.converter.bool.string;

import org.springframework.stereotype.Component;

@Component("StandardBooleanToStringConverter")
public class StandardBooleanToStringConverter extends AbstractBooleanToStringConverter {
    /**
     * 构造函数
     */
    public StandardBooleanToStringConverter() {
        super("true", "false");
    }
}
