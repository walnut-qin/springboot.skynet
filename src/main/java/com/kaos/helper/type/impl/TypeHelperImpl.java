package com.kaos.helper.type.impl;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.kaos.helper.type.TypeHelper;
import com.kaos.helper.type.entity.Age;
import com.kaos.his.enums.common.NoonEnum;

import org.springframework.stereotype.Component;

@Component
public class TypeHelperImpl implements TypeHelper {
    @Override
    public String join(String separator, Collection<String> items) {
        String ret = null;
        for (String item : items) {
            if (item == null) {
                continue;
            } else if (ret == null) {
                ret = item;
            } else {
                ret += String.format("%s%s", separator, item);
            }
        }
        return ret;
    }

    @Override
    public Age getAge(Date birthday) {
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

    @Override
    public NoonEnum getNoon(Date date) {
        // 定位目标时间
        Calendar target = Calendar.getInstance();
        target.setTime(date);

        // 定位时间：xxxx-05-01 00:00:00
        Calendar summerBegin = (Calendar) target.clone();
        summerBegin.set(Calendar.MONTH, Calendar.MAY);
        summerBegin.set(Calendar.DAY_OF_MONTH, 1);
        summerBegin.set(Calendar.HOUR_OF_DAY, 0);
        summerBegin.set(Calendar.MINUTE, 0);
        summerBegin.set(Calendar.SECOND, 0);

        // 定位时间：xxxx-10-01 00:00:00
        Calendar summerEnd = (Calendar) summerBegin.clone();
        summerEnd.set(Calendar.MONTH, Calendar.OCTOBER);

        // 定位时间：xxxx-xx-xx 08:00:00
        Calendar morningBegin = (Calendar) target.clone();
        morningBegin.set(Calendar.HOUR_OF_DAY, 8);
        morningBegin.set(Calendar.MINUTE, 0);
        morningBegin.set(Calendar.SECOND, 0);

        // 定位时间：xxxx-xx-xx 12:00:00
        Calendar morningEnd = (Calendar) morningBegin.clone();
        morningEnd.set(Calendar.HOUR_OF_DAY, 12);
        morningEnd.set(Calendar.MINUTE, 0);

        // 声明下午时间哨兵
        Calendar noonBegin = null;
        Calendar noonEnd = null;

        // 判断夏令时或冬令时
        if (target.after(summerBegin) && target.before(summerEnd)) {
            // 定位时间：xxxx-xx-xx 14:30:00
            noonBegin = (Calendar) morningEnd.clone();
            noonBegin.set(Calendar.HOUR_OF_DAY, 14);
            noonBegin.set(Calendar.MINUTE, 30);

            // 定位时间：xxxx-xx-xx 17:30:00
            noonEnd = (Calendar) noonBegin.clone();
            noonEnd.set(Calendar.HOUR_OF_DAY, 17);
        } else {
            // 定位时间：xxxx-xx-xx 14:30:00
            noonBegin = (Calendar) morningEnd.clone();
            noonBegin.set(Calendar.HOUR_OF_DAY, 14);
            noonBegin.set(Calendar.MINUTE, 0);

            // 定位时间：xxxx-xx-xx 17:30:00
            noonEnd = (Calendar) noonBegin.clone();
            noonEnd.set(Calendar.HOUR_OF_DAY, 17);
        }

        // 根据时间哨兵判断午别
        if (target.after(morningBegin) && target.before(morningEnd)) {
            return NoonEnum.上午;
        } else if (target.after(morningEnd) && target.before(noonBegin)) {
            return NoonEnum.中班;
        } else if (target.after(noonBegin) && target.before(noonEnd)) {
            return NoonEnum.下午;
        } else {
            return NoonEnum.夜班;
        }
    }

    @Override
    public <T> T getFirst(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public <T> T getLast(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(list.size() - 1);
        }
    }
}
