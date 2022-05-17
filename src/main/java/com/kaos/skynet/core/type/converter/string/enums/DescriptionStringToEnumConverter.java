package com.kaos.skynet.core.type.converter.string.enums;

import com.kaos.skynet.core.type.Enum;

import org.springframework.core.convert.converter.Converter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
public class DescriptionStringToEnumConverter<E extends Enum> implements Converter<String, E> {
    /**
     * 记录实际的E的类型
     */
    public Class<E> classOfE;

    @Override
    public E convert(String source) {
        // 轮训检查
        for (E e : classOfE.getEnumConstants()) {
            if (e.getDescription().equals(source)) {
                return e;
            }
        }
        log.error(String.format("类型转换 String -> %s : { source = %s } 异常, err = 无有效枚举值", classOfE.getName(), source));
        throw new RuntimeException("无有效枚举值");
    }
}
