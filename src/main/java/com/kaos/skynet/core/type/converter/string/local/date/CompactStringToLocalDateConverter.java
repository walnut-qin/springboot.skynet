package com.kaos.skynet.core.type.converter.string.local.date;

import org.springframework.stereotype.Component;

@Component("CompactStringToLocalDateConverter")
public class CompactStringToLocalDateConverter extends AbstractStringToLocalDateConverter {
    /**
     * 默认构造函数
     */
    public CompactStringToLocalDateConverter() {
        super("yyyyMMdd");
    }
}
