package com.kaos.skynet.core.type.converter.string.integer;

import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.stereotype.Component;

@Component("StringToLongConverter")
public class StringToLongConverter implements Converter<String, Long> {
    @Override
    public Long convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        return Long.valueOf(source);
    }
}
