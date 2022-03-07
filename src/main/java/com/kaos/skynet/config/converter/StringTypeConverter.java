package com.kaos.skynet.config.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * 该转换器将空串视为null
 */
public class StringTypeConverter implements Converter<String, String> {
    @Override
    public String convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        return source;
    }
}
