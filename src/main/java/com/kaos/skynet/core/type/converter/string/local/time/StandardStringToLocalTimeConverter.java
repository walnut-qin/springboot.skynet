package com.kaos.skynet.core.type.converter.string.local.time;

import org.springframework.stereotype.Component;

@Component("StandardStringToLocalTimeConverter")
public class StandardStringToLocalTimeConverter extends AbstractStringToLocalTimeConverter {
    /**
     * 默认构造函数
     */
    public StandardStringToLocalTimeConverter() {
        super("HH:mm:ss");
    }
}
