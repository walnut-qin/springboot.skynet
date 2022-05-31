package com.kaos.skynet.core.type.converter.string.local.datime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.kaos.skynet.core.type.converter.Converter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
public abstract class AbstractStringToLocalDateTimeConverter extends Converter<String, LocalDateTime> {
    /**
     * 格式串
     */
    String dateTimeFormat;

    @Override
    public LocalDateTime convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        // 创建格式化工具
        var fmt = DateTimeFormatter.ofPattern(dateTimeFormat);

        // 格式化
        try {
            return LocalDateTime.parse(source, fmt);
        } catch (Exception e) {
            log.error(String.format("类型转换 String -> LocalDateTime : { source = %s, format = %s } 异常, err = %s", source,
                    dateTimeFormat, e.getMessage()));
            throw new RuntimeException(e.getMessage());
        }
    }
}
