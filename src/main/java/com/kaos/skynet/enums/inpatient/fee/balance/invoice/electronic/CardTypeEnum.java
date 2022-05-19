package com.kaos.skynet.enums.inpatient.fee.balance.invoice.electronic;

import com.kaos.skynet.core.type.Enum;

public enum CardTypeEnum implements Enum {
    身份证号码("1101", "身份证号码"),
    社会保障卡号("1102", "社会保障卡号"),
    诊疗卡_就诊卡("3101", "诊疗卡_就诊卡"),
    居民户口簿("4101", "居民户口簿"),
    京通卡("5101", "京通卡");

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
    CardTypeEnum(String index, String description) {
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
