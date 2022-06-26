package com.kaos.skynet.core.util.converter;

import com.kaos.skynet.core.config.spring.exception.ConversionException;

import org.springframework.core.convert.converter.Converter;

public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        try {
            return Integer.valueOf(source);
        } catch (Exception e) {
            throw new ConversionException(String.class, Integer.class, e.getMessage());
        }
    }
}
