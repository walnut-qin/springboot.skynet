package com.kaos.skynet.core.type.converter;

public abstract class ConverterFactory<S, R> {
    /**
     * 获取对应的转换器
     * 
     * @param <T>
     * @param targetType
     * @return
     */
    public abstract <T extends R> Converter<S, T> getConverter(Class<T> targetType);

    /**
     * 转变为系统转换器工厂
     * 
     * @return
     */
    public org.springframework.core.convert.converter.ConverterFactory<S, R> transfer() {
        return new org.springframework.core.convert.converter.ConverterFactory<S, R>() {
            @Override
            public <T extends R> org.springframework.core.convert.converter.Converter<S, T> getConverter(
                    Class<T> targetType) {
                return ConverterFactory.this.getConverter(targetType).transfer();
            }
        };
    }
}
