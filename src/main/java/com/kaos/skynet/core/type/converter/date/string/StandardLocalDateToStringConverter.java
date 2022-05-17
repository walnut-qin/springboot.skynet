package com.kaos.skynet.core.type.converter.date.string;

import org.springframework.stereotype.Component;

/**
 * 标准型字符串转时间格式
 */
@Component
public class StandardLocalDateToStringConverter extends AbstractLocalDateToStringConverter {
    /**
     * 默认构造函数
     */
    public StandardLocalDateToStringConverter() {
        super("yyyy-MM-dd");
    }
}
