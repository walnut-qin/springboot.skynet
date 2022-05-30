package com.kaos.skynet.core.type.converter.local.datime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.kaos.skynet.core.type.converter.Converter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractLocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {
    /**
     * 格式串
     */
    String dateFormat;

    @Override
    public String convert(LocalDateTime source) {
        // 判空
        if (source == null) {
            return null;
        }

        // 创建格式化工具
        var fmt = DateTimeFormatter.ofPattern(dateFormat);

        // 格式化
        return source.format(fmt);
    }
}
