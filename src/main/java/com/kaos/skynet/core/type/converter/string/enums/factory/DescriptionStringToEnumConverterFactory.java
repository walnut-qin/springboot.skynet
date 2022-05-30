package com.kaos.skynet.core.type.converter.string.enums.factory;

import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.converter.string.enums.DescriptionStringToEnumConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import org.springframework.stereotype.Component;

@Component("DescriptionStringToEnumConverterFactory")
public class DescriptionStringToEnumConverterFactory implements ConverterFactory<String, Enum> {
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new DescriptionStringToEnumConverter<>(targetType);
    }
}
