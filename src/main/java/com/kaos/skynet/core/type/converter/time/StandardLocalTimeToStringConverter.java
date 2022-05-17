package com.kaos.skynet.core.type.converter.time;

import org.springframework.stereotype.Component;

/**
 * 标准型字符串转时间格式
 */
@Component
public class StandardLocalTimeToStringConverter extends AbstractLocalTimeToStringConverter {
    /**
     * 默认构造函数
     */
    public StandardLocalTimeToStringConverter() {
        super("HH:mm:ss");
    }
}
