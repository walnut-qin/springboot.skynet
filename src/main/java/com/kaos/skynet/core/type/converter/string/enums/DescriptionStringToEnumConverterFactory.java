package com.kaos.skynet.core.type.converter.string.enums;

import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.converter.Converter;
import com.kaos.skynet.core.type.converter.ConverterFactory;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Component("DescriptionStringToEnumConverterFactory")
public class DescriptionStringToEnumConverterFactory extends ConverterFactory<String, Enum> {
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<String, T>() {
            @Override
            public T convert(String source) {
                // 判空
                if (source == null) {
                    return null;
                }
                // 轮训检查
                for (T e : targetType.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                log.warn(String.format("类型转换 String -> %s : err = 无有效枚举值%s", targetType.getName(), source));
                return null;
            }
        };
    }
}
