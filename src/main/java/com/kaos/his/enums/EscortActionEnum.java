package com.kaos.his.enums;

import com.kaos.his.enums.util.IEnum;

import org.springframework.core.convert.converter.Converter;

public enum EscortActionEnum implements IEnum {
    进入("I", "进入"), 外出("O", "外出");

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
    EscortActionEnum(String index, String description) {
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
    public static class EnumConverter implements Converter<String, EscortActionEnum> {
        @Override
        public EscortActionEnum convert(String source) {
            for (EscortActionEnum e : EscortActionEnum.class.getEnumConstants()) {
                if (e.getDescription().equals(source)) {
                    return e;
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
