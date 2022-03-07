package com.kaos.skynet.enums.impl.common;

import com.kaos.skynet.enums.Enum;

public enum DeptTypeEnum implements Enum {
    门诊("C", "门诊"),
    住院("I", "住院"),
    财务("F", "财务"),
    后勤("L", "后勤"),
    药库("PI", "药库"),
    医技("T", "医技"),
    其它("O", "其它"),
    机关("D", "机关"),
    药房("P", "药房"),
    护士站("N", "护士站");

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
    DeptTypeEnum(String index, String description) {
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
}
