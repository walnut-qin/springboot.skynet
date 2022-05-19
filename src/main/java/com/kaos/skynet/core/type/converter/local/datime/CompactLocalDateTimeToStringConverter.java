package com.kaos.skynet.core.type.converter.local.datime;

import org.springframework.stereotype.Component;

/**
 * 紧凑型字符串转时间类型
 */
@Component
public class CompactLocalDateTimeToStringConverter extends AbstractLocalDateTimeToStringConverter {
    /**
     * 默认构造函数
     */
    public CompactLocalDateTimeToStringConverter() {
        super("yyyyMMddHHmmss");
    }
}
