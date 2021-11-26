package com.kaos.his.enums;

import com.kaos.his.enums.util.IEnum;

import org.springframework.core.convert.converter.Converter;

public enum SexEnum implements IEnum {
    Male("M", "男"), Female("F", "女");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;

    /**
     * 构造
     * 
     * @param index
     * @param description
     */
    SexEnum(String index, String description) {
        this.value = index;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 枚举转换器
     */
    public static class EnumConverter implements Converter<String, SexEnum> {
        @Override
        public SexEnum convert(String source) {
            for (SexEnum e : SexEnum.class.getEnumConstants()) {
                if (e.getDescription().equals(source)) {
                    return e;
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
