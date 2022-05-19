package com.kaos.skynet.core.type.converter.string.integer;

import org.springframework.core.convert.converter.Converter;

public class StringToLongConverter implements Converter<String, Long> {
    @Override
    public Long convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        return Long.valueOf(source);
    }
}
