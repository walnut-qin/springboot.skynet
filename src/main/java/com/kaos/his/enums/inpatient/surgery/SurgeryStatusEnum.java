package com.kaos.his.enums.inpatient.surgery;

import com.kaos.his.enums.IEnum;

public enum SurgeryStatusEnum implements IEnum {
    手术申请("1", "手术申请"), 手术审批("2", "手术审批"), 手术安排("3", "手术安排"), 手术完成("4", "手术完成"), 取消手术登记("5", "取消手术登记"), 手术审批未通过("6", "手术审批未通过");

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
    SurgeryStatusEnum(String index, String description) {
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
