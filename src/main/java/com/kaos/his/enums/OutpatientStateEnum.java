package com.kaos.his.enums;

import com.kaos.his.enums.util.IEnum;

/**
 * 门诊患者状态枚举
 */
public enum OutpatientStateEnum implements IEnum {
    正常挂号("N", "正常挂号"), 留观登记("R", "留观登记"), 正在留观("I", "正在留观"), 出观登记("P", "出观登记"), 留观出院完成("B", "留观出院完成"),
    留观转住院登记("E", "留观转住院登记"), 留观转住院完成("C", "留观转住院完成");

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
    OutpatientStateEnum(String value, String description) {
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
