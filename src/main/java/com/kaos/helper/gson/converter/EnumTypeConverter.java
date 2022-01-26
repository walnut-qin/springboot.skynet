package com.kaos.helper.gson.converter;

import com.kaos.inf.IEnum;

import org.springframework.core.convert.converter.Converter;

public class EnumTypeConverter<E extends IEnum> implements Converter<String, E> {
    /**
     * E的类实体
     */
    Class<E> classOfE;

    /**
     * 构造函数
     * @param classOfE
     */
    public EnumTypeConverter(Class<E> classOfE) {
        this.classOfE = classOfE;
    }

    @Override
    public E convert(String source) {
        for (E e : classOfE.getEnumConstants()) {
            if (e.getValue().equals(source) || e.getDescription().equals(source)) {
                return e;
            }
        }
        return null;
    }
}
