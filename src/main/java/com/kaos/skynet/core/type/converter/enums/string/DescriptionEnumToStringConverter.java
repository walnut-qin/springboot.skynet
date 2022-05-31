package com.kaos.skynet.core.type.converter.enums.string;

import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.stereotype.Component;

@Component("DescriptionEnumToStringConverter")
public class DescriptionEnumToStringConverter<E extends Enum> extends Converter<E, String> {
    @Override
    public String convert(E source) {
        if (source == null) {
            return null;
        }
        return source.getDescription();
    }
}
