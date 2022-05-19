package com.kaos.skynet.core.type.converter.local.date.string;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractLocalDateToStringConverter implements Converter<LocalDate, String> {
    /**
     * 格式串
     */
    String dateFormat;

    @Override
    public String convert(LocalDate source) {
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
