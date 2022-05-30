package com.kaos.skynet.core.type.converter.string.bool;

import com.kaos.skynet.core.type.converter.Converter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
public class AbstractStringToBooleanConverter implements Converter<String, Boolean> {
    /**
     * 对应true
     */
    String positiveValue;

    /**
     * 对应false
     */
    String negativeValue;

    @Override
    public Boolean convert(String source) {
        // 判空
        if (source == null || source.isEmpty()) {
            return null;
        }

        if (source.equals(positiveValue)) {
            return true;
        } else if (source.equals(negativeValue)) {
            return false;
        } else {
            log.error(String.format(
                    "类型转换 String -> Boolean : { source = %s, positiveValue = %s, negativeValue = %s } 异常, err = 值不匹配",
                    source, positiveValue, negativeValue));
            throw new RuntimeException("值不匹配");
        }
    }
}
