package com.kaos.skynet.core.type.converter.string.enums.factory;

import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.converter.string.enums.ValueStringToEnumConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class ValueStringToEnumConverterFactory implements ConverterFactory<String, Enum> {
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new ValueStringToEnumConverter<>(targetType);
    }
}
