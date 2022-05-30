package com.kaos.skynet.core.type.converter.bool.string;

import org.springframework.stereotype.Component;

@Component("ChineseBooleanToStringConverter")
public class ChineseBooleanToStringConverter extends AbstractBooleanToStringConverter{
    /**
     * 构造函数
     */
    public ChineseBooleanToStringConverter() {
        super("是", "否");
    }
}
