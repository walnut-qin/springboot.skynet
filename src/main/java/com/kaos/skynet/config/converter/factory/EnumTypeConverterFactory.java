package com.kaos.skynet.config.converter.factory;

import com.kaos.skynet.config.converter.EnumTypeConverter;
import com.kaos.skynet.enums.Enum;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class EnumTypeConverterFactory implements ConverterFactory<String, Enum> {
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new EnumTypeConverter<>(targetType);
    }
}
