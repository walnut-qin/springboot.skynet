package com.kaos.skynet.core.util.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.kaos.skynet.core.config.spring.exception.ConversionException;

import org.springframework.core.convert.converter.Converter;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    /**
     * 字符串格式
     */
    private DateTimeFormatter formatter;

    /**
     * 构造函数
     * 
     * @param format
     */
    public StringToLocalDateTimeConverter(String format) {
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public LocalDateTime convert(String source) {
        try {
            // 判空
            if (source == null) {
                return null;
            }

            // 格式化
            return LocalDateTime.parse(source, formatter);
        } catch (Exception e) {
            throw new ConversionException(String.class, LocalDateTime.class, e.getMessage());
        }
    }
}
