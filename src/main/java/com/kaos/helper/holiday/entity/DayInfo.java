package com.kaos.helper.holiday.entity;

import java.io.Serializable;
import java.util.Date;

import com.kaos.helper.holiday.enums.CodeEnum;
import com.kaos.helper.holiday.enums.DayTypeEnum;
import com.kaos.helper.holiday.enums.WeekEnum;

public class DayInfo implements Serializable {
    /**
     * 服务状态
     */
    public CodeEnum code = null;

    /**
     * Type 定义
     */
    public class Type implements Serializable {
        /**
         * 日期类型
         */
        public DayTypeEnum type = null;

        /**
         * 类型中文名
         */
        public String name = null;

        /**
         * 星期
         */
        public WeekEnum week = null;
    }

    /**
     * Type 实体
     */
    public Type type = null;

    /**
     * Holiday 定义
     */
    public class Holiday implements Serializable {
        /**
         * 是否为节假日标识
         */
        public Boolean holiday = null;

        /**
         * 节日名称
         */
        public String name = null;

        /**
         * 薪资倍数
         */
        public Integer wage = null;

        /**
         * 调休时有该字段，表示哪个节日的调休
         */
        public String target = null;

        /**
         * 调休下有该字段，true表示放完假再调休，false表示先调休再放假
         */
        public Boolean after = null;

        /**
         * 日期
         */
        public Date date = null;

        /**
         * 剩余日期
         */
        public Integer rest = null;
    }

    /**
     * 节假日实体
     */
    public Holiday holiday = null;
}
