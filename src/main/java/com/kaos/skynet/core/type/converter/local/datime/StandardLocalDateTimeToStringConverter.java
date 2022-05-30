package com.kaos.skynet.core.type.converter.local.datime;

import org.springframework.stereotype.Component;

@Component("StandardLocalDateTimeToStringConverter")
public class StandardLocalDateTimeToStringConverter extends AbstractLocalDateTimeToStringConverter {
    /**
     * 默认构造函数
     */
    public StandardLocalDateTimeToStringConverter() {
        super("yyyy-MM-dd HH:mm:ss");
    }
}
