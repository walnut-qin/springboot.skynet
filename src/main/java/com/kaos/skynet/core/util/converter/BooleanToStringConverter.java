package com.kaos.skynet.core.util.converter;

import com.kaos.skynet.core.config.spring.exception.ConversionException;

import org.springframework.core.convert.converter.Converter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BooleanToStringConverter implements Converter<Boolean, String> {
    /**
     * 真值
     */
    private String trueValue;

    /**
     * 假值
     */
    private String falseValue;

    @Override
    public String convert(Boolean source) {
        try {
            if (source == null) {
                return null;
            } else if (source) {
                return trueValue;
            } else {
                return falseValue;
            }
        } catch (Exception e) {
            throw new ConversionException(Boolean.class, String.class, e.getMessage());
        }
    }
}
