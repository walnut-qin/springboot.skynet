package com.kaos.skynet.core.type.converter.local.time;

import org.springframework.stereotype.Component;

@Component("StandardLocalTimeToStringConverter")
public class StandardLocalTimeToStringConverter extends AbstractLocalTimeToStringConverter {
    /**
     * 默认构造函数
     */
    public StandardLocalTimeToStringConverter() {
        super("HH:mm:ss");
    }
}
