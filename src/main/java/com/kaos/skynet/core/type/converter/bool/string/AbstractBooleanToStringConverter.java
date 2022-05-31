package com.kaos.skynet.core.type.converter.bool.string;

import com.kaos.skynet.core.type.converter.Converter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractBooleanToStringConverter extends Converter<Boolean, String> {
    /**
     * true时对应的值
     */
    String positiveValue;

    /**
     * false时对应的值
     */
    String negativeValue;

    @Override
    public String convert(Boolean source) {
        // 判空
        if (source == null) {
            return null;
        }

        if (source) {
            return positiveValue;
        } else {
            return negativeValue;
        }
    }
}
