package com.kaos.skynet.core.type.converter.local.time;

import org.springframework.stereotype.Component;

/**
 * 紧凑型字符串转时间类型
 */
@Component
public class CompactLocalTimeToStringConverter extends AbstractLocalTimeToStringConverter {
    /**
     * 默认构造函数
     */
    public CompactLocalTimeToStringConverter() {
        super("HHmmss");
    }
}
