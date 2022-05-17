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
     * 映射反转
     */
    Boolean inverse;

    /**
     * 构造函数
     * 
     * @param classOfE
     */
    public EnumTypeConverter(Class<E> classOfE) {
        this.classOfE = classOfE;
        this.inverse = false;
    }

    /**
     * 构造函数
     * 
     * @param classOfE
     */
    public EnumTypeConverter(Class<E> classOfE, Boolean inverse) {
        this.classOfE = classOfE;
        this.inverse = inverse;
    }

    @Override
    public E convert(String source) {
        for (E e : classOfE.getEnumConstants()) {
            if (this.inverse && e.getValue().equals(source)) {
                return e;
            } else if (e.getDescription().equals(source)) {
                return e;
            }
        }
        return null;
    }
}
