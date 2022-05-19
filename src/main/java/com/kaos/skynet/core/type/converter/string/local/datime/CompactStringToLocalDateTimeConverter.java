package com.kaos.skynet.core.type.converter.string.local.datime;

import org.springframework.stereotype.Component;

/**
 * 紧凑型字符串转时间类型
 */
@Component
public class CompactStringToLocalDateTimeConverter extends AbstractStringToLocalDateTimeConverter {
    /**
     * 默认构造函数
     */
    public CompactStringToLocalDateTimeConverter() {
        super("yyyyMMddHHmmss");
    }
}
