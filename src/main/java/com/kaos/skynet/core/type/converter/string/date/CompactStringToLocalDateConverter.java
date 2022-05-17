package com.kaos.skynet.core.type.converter.string.date;

import org.springframework.stereotype.Component;

/**
 * 紧凑型字符串转时间类型
 */
@Component
public class CompactStringToLocalDateConverter extends AbstractStringToLocalDateConverter {
    /**
     * 默认构造函数
     */
    public CompactStringToLocalDateConverter() {
        super("yyyyMMdd");
    }
}
