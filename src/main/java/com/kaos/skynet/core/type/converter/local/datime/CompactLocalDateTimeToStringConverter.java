package com.kaos.skynet.core.type.converter.local.datime;

import org.springframework.stereotype.Component;

@Component("CompactLocalDateTimeToStringConverter")
public class CompactLocalDateTimeToStringConverter extends AbstractLocalDateTimeToStringConverter {
    /**
     * 默认构造函数
     */
    public CompactLocalDateTimeToStringConverter() {
        super("yyyyMMddHHmmss");
    }
}
