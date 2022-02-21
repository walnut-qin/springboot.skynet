package com.kaos.helper.type.impl;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.kaos.helper.type.TypeHelper;
import com.kaos.helper.type.entity.Age;

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
