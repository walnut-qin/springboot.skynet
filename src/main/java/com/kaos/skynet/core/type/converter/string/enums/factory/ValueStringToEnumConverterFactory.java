package com.kaos.skynet.core.type.converter.string.enums.factory;

import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.converter.Converter;
import com.kaos.skynet.core.type.converter.ConverterFactory;
import com.kaos.skynet.core.type.converter.string.enums.ValueStringToEnumConverter;

import org.springframework.stereotype.Component;

@Component("ValueStringToEnumConverterFactory")
public class ValueStringToEnumConverterFactory implements ConverterFactory<String, Enum> {
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new ValueStringToEnumConverter<>(targetType);
    }
}
