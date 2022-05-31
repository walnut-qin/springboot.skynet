package com.kaos.skynet.core.type.converter.decimal;

import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.stereotype.Component;

@Component("FloatToStringConverter")
public class FloatToStringConverter extends Converter<Float, String> {
    @Override
    public String convert(Float source) {
        // 判空
        if (source == null) {
            return null;
        }

        return String.valueOf(source);
    }
}
