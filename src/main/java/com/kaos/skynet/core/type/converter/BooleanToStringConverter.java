package com.kaos.skynet.core.type.converter;

import org.springframework.beans.ConversionNotSupportedException;
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
            throw new ConversionNotSupportedException(source, String.class, e);
        }
    }
}
