package com.kaos.skynet.core.type.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {
    /**
     * 字符串格式
     */
    private SimpleDateFormat formatter;

    /**
     * 构造函数
     * 
     * @param format
     */
    public StringToDateConverter(String format) {
        this.formatter = new SimpleDateFormat(format);
    }

    @Override
    public Date convert(String source) {
        try {
            return formatter.parse(source);
        } catch (Exception e) {
            throw new ConversionNotSupportedException(source, String.class, e);
        }
    }
}
