package com.kaos.skynet.core.type.converter.string.bool;

import org.springframework.stereotype.Component;

@Component("ChineseStringToBooleanConverter")
public class ChineseStringToBooleanConverter extends AbstractStringToBooleanConverter {
    /**
     * 构造函数
     */
    public ChineseStringToBooleanConverter() {
        super("是", "否");
    }
}
