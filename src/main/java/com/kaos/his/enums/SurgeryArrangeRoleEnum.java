package com.kaos.his.enums;

import com.kaos.inf.IEnum;

public enum SurgeryArrangeRoleEnum implements IEnum {
    AnaesthesiaHelper("AnaesthesiaHelper", "麻醉助手"), Anaesthetist("Anaesthetist", "麻醉医生"), Helper1("Helper1", "手术助手"),
    Helper2("Helper2", "手术助手"), Helper3("Helper3", "手术助手"), ItinerantNurse("ItinerantNurse", "巡回护士"),
    ItinerantNurse1("ItinerantNurse1", "巡回护士"), Operator("Operator", "主刀医师"),
    WashingHandNurse("WashingHandNurse", "洗手护士"),
    WashingHandNurse1("WashingHandNurse1", "洗手护士");

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
    SurgeryArrangeRoleEnum(String value, String description) {
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
