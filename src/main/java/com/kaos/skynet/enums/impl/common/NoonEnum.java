package com.kaos.skynet.enums.impl.common;

import java.util.Calendar;
import java.util.Date;

import com.kaos.skynet.core.type.Enum;

/**
 * 午别
 */
public enum NoonEnum implements Enum {
    上午("1", "上午"), 下午("2", "下午"), 夜班("3", "夜班"), 中班("4", "中班");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;

    /**
     * 构造
     * 
     * @param index
     * @param description
     */
    NoonEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public static NoonEnum parse(Date date) {
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
            return 上午;
        } else if (target.after(morningEnd) && target.before(noonBegin)) {
            return 中班;
        } else if (target.after(noonBegin) && target.before(noonEnd)) {
            return 下午;
        } else {
            return 夜班;
        }
    }
}
