package com.kaos.skynet.core.util.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.core.convert.converter.Converter;

public class DateToStringConverter implements Converter<Date, String> {
    /**
     * 字符串格式
     */
    private SimpleDateFormat formatter;

    /**
     * 构造函数
     * 
     * @param format
     */
    public DateToStringConverter(String format) {
        this.formatter = new SimpleDateFormat(format);
    }

    @Override
    public String convert(Date source) {
        try {
            return formatter.format(source);
        } catch (Exception e) {
            throw new ConversionNotSupportedException(source, String.class, e);
        }
    }
}
