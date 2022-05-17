package com.kaos.skynet.core.type.converter.datime;

import org.springframework.stereotype.Component;

/**
 * 标准型字符串转时间格式
 */
@Component
public class StandardLocalDateTimeToStringConverter extends AbstractLocalDateTimeToStringConverter {
    /**
     * 默认构造函数
     */
    public StandardLocalDateTimeToStringConverter() {
        super("yyyy-MM-dd HH:mm:ss");
    }
}
