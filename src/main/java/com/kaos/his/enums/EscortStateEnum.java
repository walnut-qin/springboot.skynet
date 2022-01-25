package com.kaos.his.enums;

import com.kaos.inf.IEnum;

public enum EscortStateEnum implements IEnum {
    无核酸检测结果("0", "无核酸检测结果"), 等待院内核酸检测结果("1", "等待院内核酸检测结果"), 等待院外核酸检测结果审核("2", "等待院外核酸检测结果审核"), 生效中("3", "生效中"),
    注销("4", "注销");

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
    EscortStateEnum(String index, String description) {
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
