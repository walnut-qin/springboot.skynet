package com.kaos.his.enums.inpatient.surgery;

import com.kaos.his.enums.IEnum;

public enum SurgeryDegreeEnum implements IEnum {
    一级("1级", "一级"), 二级("2级", "二级"), 三级("3级", "三级"), 四级("4级", "四级");

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
    SurgeryDegreeEnum(String value, String description) {
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
