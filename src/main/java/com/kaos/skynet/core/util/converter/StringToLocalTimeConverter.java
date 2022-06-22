package com.kaos.skynet.core.util.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.core.convert.converter.Converter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    /**
     * 字符串格式
     */
    private DateTimeFormatter formatter;

    /**
     * 构造函数
     * 
     * @param format
     */
    public StringToLocalTimeConverter(String format) {
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public LocalTime convert(String source) {
        try {
            // 判空
            if (source == null) {
                return null;
            }

            // 格式化
            return LocalTime.parse(source, formatter);
        } catch (Exception e) {
            throw new ConversionNotSupportedException(source, String.class, e);
        }
    }
}
