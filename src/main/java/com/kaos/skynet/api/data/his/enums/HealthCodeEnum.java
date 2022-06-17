package com.kaos.skynet.api.data.his.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HealthCodeEnum implements Enum {
    绿码("0", "绿码"),
    黄码("1", "黄码"),
    红码("2", "红码");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
