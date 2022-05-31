package com.kaos.skynet.core.type.converter;

/**
 * 自定义转换器类型
 * 原因：使用系统的Converter接口实现的转换器会被自动注入到Rest解析器
 */
public interface Converter<S, T> {
    /**
     * 类型转换
     */
    T convert(S key);
}
