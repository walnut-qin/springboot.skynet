package com.kaos.skynet.core.type.converter.string.local.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
public abstract class AbstractStringToLocalTimeConverter implements Converter<String, LocalTime> {
    /**
     * 格式串
     */
    String timeFormat;

    @Override
    public LocalTime convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        // 创建格式化工具
        var fmt = DateTimeFormatter.ofPattern(timeFormat);

        // 格式化
        try {
            return LocalTime.parse(source, fmt);
        } catch (Exception e) {
            log.error(String.format("类型转换 String -> LocalTime : { source = %s, format = %s } 异常, err = %s", source,
                    timeFormat, e.getMessage()));
            throw new RuntimeException(e.getMessage());
        }
    }
}
