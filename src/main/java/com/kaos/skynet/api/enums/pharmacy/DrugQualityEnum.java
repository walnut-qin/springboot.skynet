package com.kaos.skynet.api.enums.pharmacy;

import com.kaos.skynet.core.type.Enum;

public enum DrugQualityEnum implements Enum {
    麻醉药("1", "麻醉药"), 保管药("2", "保管药"), 普药("3", "普药"), 输液("4", "输液"), 精神药品("5", "精神药品"), 毒药("6", "毒药"), 医保药("7", "医保药"),
    易制毒("8", "易制毒"), 放射性药品("9", "放射性药品"), 计划生育药品("E", "计划生育药品"), 其它("E3", "其它"), 一类精神药品("P1", "一类精神药品"),
    二类精神药品("P2", "二类精神药品"), 毒麻药("S", "毒麻药"), 大输液("T", "大输液"), 贵重药("V", "贵重药");

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
    DrugQualityEnum(String value, String description) {
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
