package com.kaos.his.enums;

/**
 * 枚举：职级
 */
public enum RankEnum implements IEnum {
    特殊津贴专家("1", "特殊津贴专家"), 主任医师("2", "主任医师"), 副主任医师("3", "副主任医师"), 主治医师("4", "主治医师"), 医师("5", "医师"),
    见习医师("6", "见习医师"), 副主任护师("7", "副主任护师"), 主管护师("8", "主管护师"), 护师("9", "护师");

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
    RankEnum(String value, String description) {
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
}
