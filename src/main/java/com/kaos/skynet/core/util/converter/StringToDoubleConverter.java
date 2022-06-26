package com.kaos.skynet.core.util.converter;

import com.kaos.skynet.core.config.spring.exception.ConversionException;

import org.springframework.core.convert.converter.Converter;

public class StringToDoubleConverter implements Converter<String, Double> {

    @Override
    public Double convert(String source) {
        try {
            // 支持空值
            if (source == null) {
                return null;
            }

            // 执行转换
            return Double.valueOf(source);
        } catch (Exception e) {
            throw new ConversionException(String.class, Double.class, e.getMessage());
        }
    }
}
