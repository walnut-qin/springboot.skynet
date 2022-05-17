package com.kaos.skynet.core.type.converter.date.string;

import org.springframework.stereotype.Component;

/**
 * 紧凑型字符串转时间类型
 */
@Component
public class CompactLocalDateToStringConverter extends AbstractLocalDateToStringConverter {
    /**
     * 默认构造函数
     */
    public CompactLocalDateToStringConverter() {
        super("yyyyMMdd");
    }
}
