package com.kaos.helper.holiday.entity;

import java.util.Date;

import com.kaos.helper.holiday.enums.CodeEnum;
import com.kaos.helper.holiday.enums.DayTypeEnum;
import com.kaos.helper.holiday.enums.WeekEnum;

public class DayInfo {
    /**
     * 服务状态
     */
    public CodeEnum code = null;

    /**
     * Type 定义
     */
    public class Type {
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
    public class Holiday {
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
