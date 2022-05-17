package com.kaos.skynet.core.type.converter.string.date;

import org.springframework.stereotype.Component;

/**
 * 标准型字符串转时间格式
 */
@Component
public class StandardStringToLocalDateConverter extends AbstractStringToLocalDateConverter {
    /**
     * 默认构造函数
     */
    public StandardStringToLocalDateConverter() {
        super("yyyy-MM-dd");
    }
}
