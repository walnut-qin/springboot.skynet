package com.kaos.skynet.core.type.converter.string.decimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDoubleConverter implements Converter<String, Double> {
    @Override
    public Double convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        return Double.valueOf(source);
    }
}
