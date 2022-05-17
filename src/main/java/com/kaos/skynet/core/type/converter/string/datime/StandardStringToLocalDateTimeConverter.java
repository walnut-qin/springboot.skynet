package com.kaos.skynet.core.type.converter.string.datime;

import org.springframework.stereotype.Component;

/**
 * 标准型字符串转时间格式
 */
@Component
public class StandardStringToLocalDateTimeConverter extends AbstractStringToLocalDateTimeConverter {
    /**
     * 默认构造函数
     */
    public StandardStringToLocalDateTimeConverter() {
        super("yyyy-MM-dd HH:mm:ss");
    }
}
