package com.kaos.skynet.core.type.converter.string.local.date;

import org.springframework.stereotype.Component;

@Component("StandardStringToLocalDateConverter")
public class StandardStringToLocalDateConverter extends AbstractStringToLocalDateConverter {
    /**
     * 默认构造函数
     */
    public StandardStringToLocalDateConverter() {
        super("yyyy-MM-dd");
    }
}
