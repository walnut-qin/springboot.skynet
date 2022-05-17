package com.kaos.skynet.core.type.converter.string.time;

import org.springframework.stereotype.Component;

/**
 * 紧凑型字符串转时间类型
 */
@Component
public class CompactStringToLocalTimeConverter extends AbstractStringToLocalTimeConverter {
    /**
     * 默认构造函数
     */
    public CompactStringToLocalTimeConverter() {
        super("HHmmss");
    }
}
