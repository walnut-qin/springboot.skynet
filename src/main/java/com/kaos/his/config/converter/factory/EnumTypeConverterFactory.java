package com.kaos.his.config.converter.factory;

import com.kaos.his.config.converter.EnumTypeConverter;
import com.kaos.his.enums.IEnum;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class EnumTypeConverterFactory implements ConverterFactory<String, IEnum> {
    @Override
    public <T extends IEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new EnumTypeConverter<>(targetType);
    }
}
