package com.kaos.skynet.plugin.timor.entity;

import java.util.Date;

import com.google.gson.annotations.JsonAdapter;
import com.kaos.skynet.core.json.gson.adapter.enums.ValueEnumTypeAdapter;
import com.kaos.skynet.plugin.timor.enums.DayTypeEnum;
import com.kaos.skynet.plugin.timor.enums.ResultCodeEnum;
import com.kaos.skynet.plugin.timor.enums.WeekEnum;

import lombok.Getter;

@Getter
public class DayInfo {
    /**
     * 服务状态
     */
    @JsonAdapter(ValueEnumTypeAdapter.class)
    private ResultCodeEnum code;

    /**
     * Type 实体
     */
    private Type type;

    /**
     * Type 定义
     */
    @Getter
    public class Type {
        /**
         * 日期类型
         */
        @JsonAdapter(ValueEnumTypeAdapter.class)
        private DayTypeEnum type;

        /**
         * 类型中文名
         */
        private String name;

        /**
         * 星期
         */
        @JsonAdapter(ValueEnumTypeAdapter.class)
        private WeekEnum week;
    }

    /**
     * 节假日实体
     */
    private Holiday holiday;

    /**
     * Holiday 定义
     */
    @Getter
    public class Holiday {
        /**
         * 是否为节假日标识
         */
        private Boolean holiday;

        /**
         * 节日名称
         */
        private String name;

        /**
         * 薪资倍数
         */
        private Integer wage;

        /**
         * 调休时有该字段，表示哪个节日的调休
         */
        private String target;

        /**
         * 调休下有该字段，true表示放完假再调休，false表示先调休再放假
         */
        private Boolean after;

        /**
         * 日期
         */
        private Date date;

        /**
         * 剩余日期
         */
        private Integer rest;
    }
}
