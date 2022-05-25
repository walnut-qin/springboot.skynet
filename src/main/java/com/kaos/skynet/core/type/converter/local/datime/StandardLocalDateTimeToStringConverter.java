package com.kaos.skynet.core.type.converter.local.datime;

public class StandardLocalDateTimeToStringConverter extends AbstractLocalDateTimeToStringConverter {
    /**
     * 默认构造函数
     */
    public StandardLocalDateTimeToStringConverter() {
        super("yyyy-MM-dd HH:mm:ss");
    }
}
