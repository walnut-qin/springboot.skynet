package com.kaos.skynet.config.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

public class LocalDateTimeTypeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        // 创建格式化工具
        var fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化
        try {
            return LocalDateTime.parse(source, fmt);
        } catch (Exception e) {
            throw new RuntimeException(String.format("格式化LocalDateTime失败(%s)", source));
        }
    }
}
