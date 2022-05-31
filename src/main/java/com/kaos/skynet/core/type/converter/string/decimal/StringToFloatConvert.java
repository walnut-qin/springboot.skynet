package com.kaos.skynet.core.type.converter.string.decimal;

import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.stereotype.Component;

@Component("StringToFloatConvert")
public class StringToFloatConvert extends Converter<String, Float> {
    @Override
    public Float convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        return Float.valueOf(source);
    }
}
