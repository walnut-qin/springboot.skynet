package com.kaos.skynet.core.type.converter.decimal;

import org.springframework.core.convert.converter.Converter;

public class DoubleToStringConverter implements Converter<Double, String> {
    @Override
    public String convert(Double source) {
        // 判空
        if (source == null) {
            return null;
        }

        return String.valueOf(source);
    }
}
