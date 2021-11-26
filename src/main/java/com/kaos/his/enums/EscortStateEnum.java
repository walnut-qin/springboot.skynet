package com.kaos.his.enums;

import com.kaos.his.enums.util.IEnum;

import org.springframework.core.convert.converter.Converter;

public enum EscortStateEnum implements IEnum {
    无核酸检测结果("0", "无核酸检测结果"), 等待院内核酸检测结果("1", "等待院内核酸检测结果"), 等待院外核酸检测结果审核("2", "等待院外核酸检测结果审核"), 生效中("3", "生效中"),
    注销("4", "注销");

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
    EscortStateEnum(String index, String description) {
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
    public static class EnumConverter implements Converter<String, EscortStateEnum> {
        @Override
        public EscortStateEnum convert(String source) {
            for (EscortStateEnum e : EscortStateEnum.class.getEnumConstants()) {
                if (e.getDescription().equals(source)) {
                    return e;
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
