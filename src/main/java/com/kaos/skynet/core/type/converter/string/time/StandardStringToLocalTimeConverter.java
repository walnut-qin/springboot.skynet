package com.kaos.skynet.core.type.converter.string.time;

import org.springframework.stereotype.Component;

/**
 * 标准型字符串转时间格式
 */
@Component
public class StandardStringToLocalTimeConverter extends AbstractStringToLocalTimeConverter {
    /**
     * 默认构造函数
     */
    public StandardStringToLocalTimeConverter() {
        super("HH:mm:ss");
    }
}
