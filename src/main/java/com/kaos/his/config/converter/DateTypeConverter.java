package com.kaos.his.config.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateTypeConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        // 创建格式化工具
        var fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 格式化
        try {
            return fmt.parse(source);
        } catch (Exception e) {
            throw new RuntimeException(String.format("格式化Date参数失败(%s)", e.getMessage()));
        }
    }
}
