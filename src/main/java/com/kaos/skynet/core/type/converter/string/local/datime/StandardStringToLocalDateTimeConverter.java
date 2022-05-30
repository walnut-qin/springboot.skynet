package com.kaos.skynet.core.type.converter.string.local.datime;

import org.springframework.stereotype.Component;

@Component("StandardStringToLocalDateTimeConverter")
public class StandardStringToLocalDateTimeConverter extends AbstractStringToLocalDateTimeConverter {
    /**
     * 默认构造函数
     */
    public StandardStringToLocalDateTimeConverter() {
        super("yyyy-MM-dd HH:mm:ss");
    }
}
