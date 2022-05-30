package com.kaos.skynet.core.type.converter.string.local.time;

import org.springframework.stereotype.Component;

@Component("CompactStringToLocalTimeConverter")
public class CompactStringToLocalTimeConverter extends AbstractStringToLocalTimeConverter {
    /**
     * 默认构造函数
     */
    public CompactStringToLocalTimeConverter() {
        super("HHmmss");
    }
}
