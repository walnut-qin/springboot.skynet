package com.kaos.skynet.core.type.converter.string.local.datime;

import org.springframework.stereotype.Component;

@Component("CompactStringToLocalDateTimeConverter")
public class CompactStringToLocalDateTimeConverter extends AbstractStringToLocalDateTimeConverter {
    /**
     * 默认构造函数
     */
    public CompactStringToLocalDateTimeConverter() {
        super("yyyyMMddHHmmss");
    }
}
