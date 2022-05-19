package com.kaos.skynet.core.type.converter.string.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
public abstract class AbstractStringToDateConverter implements Converter<String, Date> {
    /**
     * 时间格式
     */
    String format;

    @Override
    public Date convert(String source) {
        // 构造时间格式化工具
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        try {
            return formatter.parse(source);
        } catch (Exception e) {
            log.warn(String.format("String转Date失败(%s)", e.getMessage()));
            return null;
        }
    }
}
