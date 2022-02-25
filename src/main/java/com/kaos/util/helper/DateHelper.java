package com.kaos.util.helper;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.kaos.his.enums.impl.common.NoonEnum;

import org.javatuples.Triplet;
import org.springframework.validation.annotation.Validated;

/**
 * Date类型的助手
 */
public interface DateHelper {
    /**
     * 年龄实体
     */
    @Validated
    public static class Age {
        /**
         * 年龄数据
         */
        @NotNull
        Triplet<Integer, Integer, Integer> data = null;

        /**
         * 构造函数
         * 
         * @param year  年
         * @param month 月
         * @param day   日
         */
        public Age(Integer year, Integer month, Integer day) {
            this.data = new Triplet<Integer, Integer, Integer>(
                    Optional.fromNullable(year).or(0),
                    Optional.fromNullable(month).or(0),
                    Optional.fromNullable(day).or(0));
        }

        /**
         * 获取年龄的年份数
         * 
         * @return
         */
        public Integer getAge() {
            return this.data.getValue0();
        }

        /**
         * 转为字符串描述
         */
        public String toString() {
            return String.format("%s%s%s",
                    this.data.getValue0().equals(0) ? "" : this.data.getValue0() + "岁",
                    this.data.getValue1().equals(0) ? "" : this.data.getValue1() + "月",
                    this.data.getValue2().equals(0) ? "" : this.data.getValue2() + "天");
        }
    }

    /**
     * 获取年龄信息
     * 
     * @param birthday 生日
     * @return
     */
    Age getAge(Date birthday);

    /**
     * 获取午别信息
     * 
     * @param date 目标时间
     * @return
     */
    NoonEnum getNoon(Date date);
}
