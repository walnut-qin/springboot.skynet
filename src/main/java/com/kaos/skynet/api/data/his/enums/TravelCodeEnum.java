package com.kaos.skynet.api.data.his.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TravelCodeEnum implements Enum {
    正常("0", "正常"), 带星号("1", "带星号"), 黄码("2", "黄码"), 红码("3", "红码");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
