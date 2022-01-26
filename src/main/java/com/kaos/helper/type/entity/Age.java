package com.kaos.helper.type.entity;

import com.google.common.base.Optional;

public class Age {
    /**
     * 岁数
     */
    Integer year = null;

    /**
     * 月份数
     */
    Integer month = null;

    /**
     * 日期数
     */
    Integer day = null;

    /**
     * 构造函数
     * 
     * @param year  年
     * @param month 月
     * @param day   日
     */
    public Age(Integer year, Integer month, Integer day) {
        this.year = Optional.fromNullable(year).or(0);
        this.month = Optional.fromNullable(month).or(0);
        this.day = Optional.fromNullable(day).or(0);
    }

    /**
     * 获取年龄的年份数
     * 
     * @return
     */
    public Integer getAge() {
        return this.year;
    }

    /**
     * 转为字符串描述
     */
    public String toString() {
        return String.format("%s%s%s",
                this.year.equals(0) ? "" : this.year + "岁",
                this.month.equals(0) ? "" : this.month + "月",
                this.day.equals(0) ? "" : this.day + "天");
    }
}
