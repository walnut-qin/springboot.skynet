package com.kaos.his.enums.pharmacy;

import com.kaos.his.enums.IEnum;

public enum DrugTypeEnum implements IEnum {
    中草药("C", "中草药"), 卫生材料("D", "卫生材料"), 自制药品("E", "自制药品"), 化验药品("F", "化验药品"), 制剂用品("G", "制剂用品"), 西药("P", "西药"),
    中成药("Z", "中成药");

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
    DrugTypeEnum(String value, String description) {
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
