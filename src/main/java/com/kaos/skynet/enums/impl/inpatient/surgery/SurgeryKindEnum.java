package com.kaos.skynet.enums.impl.inpatient.surgery;

import com.kaos.skynet.core.type.Enum;

public enum SurgeryKindEnum implements Enum {
    普通("1", "普通"), 急诊("2", "急诊"), 日间("3", "日间"), 痔瘘("4", "痔瘘"), 预约出院("5", "腔镜"), 择期("6", "择期");

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
    SurgeryKindEnum(String value, String description) {
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
