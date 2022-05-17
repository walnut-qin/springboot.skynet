package com.kaos.skynet.core.type.converter.enums.string;

import com.kaos.skynet.core.type.Enum;

import org.springframework.core.convert.converter.Converter;

public class DescriptionEnumToStringConverter<E extends Enum> implements Converter<E, String> {
    @Override
    public String convert(E source) {
        if (source == null) {
            return null;
        }
        return source.getDescription();
    }
}
