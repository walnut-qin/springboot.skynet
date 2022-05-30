package com.kaos.skynet.core.type.converter.local.date.string;

import org.springframework.stereotype.Component;

@Component("StandardLocalDateToStringConverter")
public class StandardLocalDateToStringConverter extends AbstractLocalDateToStringConverter {
    /**
     * 默认构造函数
     */
    public StandardLocalDateToStringConverter() {
        super("yyyy-MM-dd");
    }
}
