package com.kaos.skynet.core.type.converter.decimal;

import org.springframework.core.convert.converter.Converter;

public class FloatToStringConverter implements Converter<Float, String> {
    @Override
    public String convert(Float source) {
        // 判空
        if (source == null) {
            return null;
        }

        return String.valueOf(source);
    }
}
