package com.kaos.skynet.api.enums.inpatient.surgery;

import com.kaos.skynet.core.type.Enum;

/**
 * 手术检验结果
 */
public enum SurgeryInspectResultEnum implements Enum {
    否("1", "否"), HAV("2", "HAV"), HBV("3", "HBV"), HCV("4", "HCV"), HIV("5", "HIV");

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
    SurgeryInspectResultEnum(String value, String description) {
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
