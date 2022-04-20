package com.kaos.skynet.config.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

public class LocalDateTypeConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        // 创建格式化工具
        var fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 格式化
        try {
            return LocalDate.parse(source, fmt);
        } catch (Exception e) {
            throw new RuntimeException(String.format("格式化LocalDate失败(%s)", source));
        }
    }
}
