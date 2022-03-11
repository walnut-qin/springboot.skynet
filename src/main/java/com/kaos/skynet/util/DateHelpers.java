package com.kaos.skynet.util;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import org.javatuples.Pair;
import org.javatuples.Triplet;

public final class DateHelpers {
    /**
     * 获取年龄
     * 
     * @param birthday
     * @return
     */
    public static Age getAge(Date birthday) {
        // 合法性判断
        if (birthday == null || new Date().before(birthday)) {
            throw new InvalidParameterException("入参异常");
        }

        // 声明参与计算的参数
        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthday);

        // 计算天数
        Integer day = now.get(Calendar.DATE) - birth.get(Calendar.DATE);
        if (day < 0) {
            now.add(Calendar.MONTH, -1);
            day += now.getActualMaximum(Calendar.DATE);
        }

        // 计算月数
        Integer month = now.get(Calendar.MONTH) - birth.get(Calendar.MONTH);
        if (month < 0) {
            now.add(Calendar.YEAR, -1);
            month += now.getActualMaximum(Calendar.MONTH);
        }

        // 计算年份
        Integer year = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

        return new Age(year, month, day);
    }

    /**
     * 年龄实体
     */
    public static class Age {
        /**
         * 年龄数据
         */
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
     * 分割时段，分段之间无重叠，最小识别间隔1s
     * eg.
     * 原段:
     * [2000-01-01 00:10:00, 2000-01-01 03:10:59]
     * 拆分:
     * [2000-01-01 00:10:00, 2000-01-01 00:59:59],
     * [2000-01-01 01:00:00, 2000-01-01 01:59:59],
     * [2000-01-01 02:00:00, 2000-01-01 02:59:59],
     * [2000-01-01 03:00:00, 2000-01-01 03:10:59]
     * 
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param interval  时间间隔, 单位秒
     * @return
     */
    public static List<Pair<Date, Date>> splitInHours(Date beginDate, Date endDate) {
        // 合法性判断
        if (beginDate == null || endDate == null) {
            throw new RuntimeException("开始时间和结束时间不能为空");
        } else if (endDate.before(beginDate)) {
            throw new RuntimeException("结束时间不能早于开始时间");
        }

        // 创建结果集
        List<Pair<Date, Date>> ret = Lists.newArrayList();

        // 创建遍历指针
        Calendar ptr = Calendar.getInstance();
        ptr.setTime(beginDate);

        // 分段
        while (true) {
            // 记录分段起点
            Calendar segBegin = (Calendar) ptr.clone();

            // 计算分段终点
            ptr.add(Calendar.HOUR, 1);
            ptr.set(Calendar.MINUTE, 0);
            ptr.set(Calendar.SECOND, 0);

            // 记录分段终点
            Calendar segEnd = (Calendar) ptr.clone();
            if (segEnd.getTime().before(endDate)) {
                segEnd.add(Calendar.SECOND, -1);
                ret.add(new Pair<Date, Date>(segBegin.getTime(), segEnd.getTime()));
            } else {
                ret.add(new Pair<Date, Date>(segBegin.getTime(), endDate));
                break;
            }
        }

        return ret;
    }
}
