package com.kaos.skynet.config.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

public class LocalTimeTypeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        // 创建格式化工具
        var fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        // 格式化
        try {
            return LocalTime.parse(source, fmt);
        } catch (Exception e) {
            throw new RuntimeException(String.format("格式化LocalTime失败(%s)", source));
        }
    }
}
