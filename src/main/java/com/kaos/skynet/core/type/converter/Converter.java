package com.kaos.skynet.core.type.converter;

/**
 * 自定义转换器类型
 * 原因：使用系统的Converter接口实现的转换器会被自动注入到Rest解析器
 */
public abstract class Converter<S, T> {
    /**
     * 类型转换
     */
    public abstract T convert(S key);

    /**
     * 转变为系统转换器
     * 
     * @return
     */
    public org.springframework.core.convert.converter.Converter<S, T> transfer() {
        return new org.springframework.core.convert.converter.Converter<S, T>() {
            @Override
            public T convert(S source) {
                return Converter.this.convert(source);
            }
        };
    }
}
