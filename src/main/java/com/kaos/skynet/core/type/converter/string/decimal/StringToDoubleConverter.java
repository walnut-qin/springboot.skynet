package com.kaos.skynet.core.type.converter.string.decimal;

import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.stereotype.Component;

@Component("StringToDoubleConverter")
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
