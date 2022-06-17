package com.kaos.skynet.api.data.his.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HospitalTypeEnum implements Enum {
    综合医院("0", "综合医院"),
    中医医院("1", "中医医院"),
    中西医结合医院("2", "中西医结合医院"),
    名族医医院("3", "名族医医院"),
    专科医院("4", "专科医院"),
    康复医院("5", "康复医院");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}
