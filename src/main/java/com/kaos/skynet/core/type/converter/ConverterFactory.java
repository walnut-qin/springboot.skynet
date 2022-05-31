package com.kaos.skynet.core.type.converter;

public interface ConverterFactory<S, R> {
    /**
     * 获取对应的转换器
     * 
     * @param <T>
     * @param targetType
     * @return
     */
    public abstract <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
