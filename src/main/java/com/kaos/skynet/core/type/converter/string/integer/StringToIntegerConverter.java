package com.kaos.skynet.core.type.converter.string.integer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("StringToIntegerConverter")
public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        return Integer.valueOf(source);
    }
}
