package com.kaos.skynet.core.type.converter;

import org.springframework.beans.ConversionNotSupportedException;
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
            throw new ConversionNotSupportedException(source, String.class, e);
        }
    }
}
