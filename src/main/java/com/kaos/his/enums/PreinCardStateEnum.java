package com.kaos.his.enums;

import com.kaos.his.enums.util.IEnum;

import org.springframework.core.convert.converter.Converter;

public enum PreinCardStateEnum implements IEnum {
    预约("0", "预约"), 作废("1", "作废"), 转住院("2", "转住院"), 签床("3", "签床"), 预住院预约("4", "预住院预约");

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
    PreinCardStateEnum(String value, String description) {
        this.value = value;
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
    public static class EnumConverter implements Converter<String, PreinCardStateEnum> {
        @Override
        public PreinCardStateEnum convert(String source) {
            for (PreinCardStateEnum e : PreinCardStateEnum.class.getEnumConstants()) {
                if (e.getDescription().equals(source)) {
                    return e;
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
