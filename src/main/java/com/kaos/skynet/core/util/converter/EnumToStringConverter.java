package com.kaos.skynet.core.util.converter;

import com.kaos.skynet.core.config.spring.exception.ConversionException;
import com.kaos.skynet.core.type.Enum;

import org.springframework.core.convert.converter.Converter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnumToStringConverter<E extends Enum> implements Converter<E, String> {
    /**
     * 使用值域，反之使用描述域
     */
    private Boolean useValue;

    @Override
    public String convert(E source) {
        try {
            if (source == null) {
                return null;
            }
            return useValue ? source.getValue() : source.getDescription();
        } catch (Exception e) {
            throw new ConversionException(source.getClass(), String.class, e.getMessage());
        }
    }
}
