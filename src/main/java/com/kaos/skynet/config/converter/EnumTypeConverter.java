package com.kaos.skynet.config.converter;

import com.kaos.skynet.enums.Enum;

import org.springframework.core.convert.converter.Converter;

/**
 * 枚举转换器，整个系统的枚举转换核心规则
 */
public class EnumTypeConverter<E extends Enum> implements Converter<String, E> {
    /**
     * E的类实体
     */
    Class<E> classOfE;

    /**
     * 使用value映射，反之使用description映射
     */
    Boolean useValue;

    /**
     * 构造函数
     * 
     * @param classOfE
     */
    public EnumTypeConverter(Class<E> classOfE, Boolean useValue) {
        this.classOfE = classOfE;
        this.useValue = useValue;
    }

    @Override
    public E convert(String source) {
        for (E e : classOfE.getEnumConstants()) {
            if (this.useValue && e.getValue().equals(source)) {
                return e;
            } else if (e.getDescription().equals(source)) {
                return e;
            }
        }
        return null;
    }
}
