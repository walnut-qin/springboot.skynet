package com.kaos.skynet.core.type.converter.date.string;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kaos.skynet.core.type.converter.Converter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractDateToStringConverter implements Converter<Date, String> {
    /**
     * 时间格式
     */
    String format;

    @Override
    public String convert(Date source) {
        // 构造时间格式化工具
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        return formatter.format(source);
    }
}
