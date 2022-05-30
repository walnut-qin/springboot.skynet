package com.kaos.skynet.core.type.converter.local.time;

import org.springframework.stereotype.Component;

@Component("CompactLocalTimeToStringConverter")
public class CompactLocalTimeToStringConverter extends AbstractLocalTimeToStringConverter {
    /**
     * 默认构造函数
     */
    public CompactLocalTimeToStringConverter() {
        super("HHmmss");
    }
}
