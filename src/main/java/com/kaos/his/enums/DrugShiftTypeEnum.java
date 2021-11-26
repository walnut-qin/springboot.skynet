package com.kaos.his.enums;

import com.kaos.his.enums.util.IEnum;

import org.springframework.core.convert.converter.Converter;

public enum DrugShiftTypeEnum implements IEnum {
    更新("U", "更新"), 特殊修改("M", "特殊修改"), 新药("N", "新药"), 停用("S", "停用"), 调价("A", "调价");

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
    DrugShiftTypeEnum(String index, String description) {
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
    public static class EnumConverter implements Converter<String, DrugShiftTypeEnum> {
        @Override
        public DrugShiftTypeEnum convert(String source) {
            for (DrugShiftTypeEnum e : DrugShiftTypeEnum.class.getEnumConstants()) {
                if (e.getDescription().equals(source)) {
                    return e;
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
