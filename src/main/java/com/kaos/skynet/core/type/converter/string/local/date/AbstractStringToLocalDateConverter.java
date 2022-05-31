package com.kaos.skynet.core.type.converter.string.local.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.kaos.skynet.core.type.converter.Converter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
public abstract class AbstractStringToLocalDateConverter extends Converter<String, LocalDate> {
    /**
     * 格式串
     */
    String dateFormat;

    @Override
    public LocalDate convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        // 创建格式化工具
        var fmt = DateTimeFormatter.ofPattern(dateFormat);

        // 格式化
        try {
            return LocalDate.parse(source, fmt);
        } catch (Exception e) {
            log.error(String.format("类型转换 String -> LocalDate : { source = %s, format = %s } 异常, err = %s", source,
                    dateFormat, e.getMessage()));
            throw new RuntimeException(e.getMessage());
        }
    }
}
