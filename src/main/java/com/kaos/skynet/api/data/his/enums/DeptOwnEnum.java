package com.kaos.skynet.api.data.his.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeptOwnEnum implements Enum {
    All("0", "全院区"),
    Sourth("1", "南院区"),
    North("2", "北院区"),
    East("3", "东津院区");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
