package com.kaos.his.enums.inpatient.surgery;

import com.kaos.inf.IEnum;

public enum MetOpsInciTypeEnum implements IEnum {
    I类切口("1", "I类切口"), II类切口("2", "II类切口"), III类切口("3", "III类切口"), IV类切口("4", "IV类切口"), 零类切口("5", "零类切口");

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
    MetOpsInciTypeEnum(String value, String description) {
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
