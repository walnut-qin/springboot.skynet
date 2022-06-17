package com.kaos.skynet.api.data.docare.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SurgeryLevelEnum implements Enum {
    I("1", "一级"),
    II("2", "二级"),
    III("3", "三级"),
    IV("4", "四级");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
