package com.kaos.skynet.enums.impl.inpatient.surgery;

import com.kaos.skynet.core.type.Enum;

public enum AnesTypeEnum implements Enum {
    全麻("1", "全麻"), 局麻("2", "局麻"), 硬膜外麻醉("3", "硬膜外麻醉"), 腰麻("4", "腰麻"), 氯胺酮("5", "氯胺酮"), 臂丛麻醉("6", "臂丛麻醉"),
    基础麻醉("7", "基础麻醉"),
    硬膜外封闭("8", "硬膜外封闭"), 气管内麻醉("9", "气管内麻醉"), 腰硬联合麻醉("10", "腰硬联合麻醉"), 静脉麻醉("11", "静脉麻醉"),
    颈丛麻醉("12", "颈丛麻醉"), 局麻_心电监护("13", "局麻+心电监护"), 局麻_备气管内麻("14", "局麻(备气管内麻)"), 测试麻醉("15", "测试麻醉"), 缺省("16", "-");

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
    AnesTypeEnum(String value, String description) {
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
