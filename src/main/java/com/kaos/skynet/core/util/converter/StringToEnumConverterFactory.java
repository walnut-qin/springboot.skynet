package com.kaos.skynet.core.util.converter;

import com.kaos.skynet.core.config.spring.exception.ConversionException;
import com.kaos.skynet.core.type.Enum;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {
    /**
     * 使用值域，反之使用描述域
     */
    private Boolean useValue;

    @Override
    public <E extends Enum> Converter<String, E> getConverter(Class<E> targetType) {
        return new Converter<String, E>() {
            @Override
            public E convert(String source) {
                try {
                    // 判空
                    if (source == null) {
                        return null;
                    }

                    // 轮训检查
                    for (E e : targetType.getEnumConstants()) {
                        if (useValue) {
                            if (e.getValue().equals(source)) {
                                return e;
                            }
                        } else {
                            if (e.getDescription().equals(source)) {
                                return e;
                            }
                        }
                    }

                    return null;
                } catch (Exception e) {
                    throw new ConversionException(targetType, String.class, e.getMessage());
                }
            }
        };
    }
}
