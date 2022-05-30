package com.kaos.skynet.core.type.converter.local.date.string;

import org.springframework.stereotype.Component;

@Component("CompactLocalDateToStringConverter")
public class CompactLocalDateToStringConverter extends AbstractLocalDateToStringConverter {
    /**
     * 默认构造函数
     */
    public CompactLocalDateToStringConverter() {
        super("yyyyMMdd");
    }
}
