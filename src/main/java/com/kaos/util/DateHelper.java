package com.kaos.util;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    /**
     * 计算年龄
     * 
     * @param birthday
     * @return
     */
    public static int GetAge(Date birthday) {
        // 声明参与计算的参数
        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthday);
        Integer yearl = birth.get(Calendar.YEAR);
        Integer yearr = now.get(Calendar.YEAR);
        Integer monthl = birth.get(Calendar.MONTH);
        Integer monthr = now.get(Calendar.MONTH);
        Integer dayl = birth.get(Calendar.DATE);
        Integer dayr = now.get(Calendar.DATE);

        // 合法性判断
        if (now.before(birth)) {
            throw new InvalidParameterException("计算年龄参数错误，当前时间早于出生日期");
        }

        // 计算年份差
        Integer age = yearr - yearl;

        // 计算补正
        if (monthr < monthl || monthr == monthl && dayr < dayl) {
            age--;
        }

        return age;
    }

    /**
     * 计算年龄明细
     * @param birthday
     * @return
     */
    public static String GetAgeDetail(Date birthday) {
        // 声明参与计算的参数
        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();

        // 合法性判断
        if (now.before(birth)) {
            throw new InvalidParameterException("计算年龄参数错误，当前时间早于出生日期");
        }

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

        return String.format("{0}{1}{2}", year == 0 ? "" : year.toString() + "岁", month == 0 ? "" : month + "月",
                day == 0 ? "" : day + "天");
    }
}