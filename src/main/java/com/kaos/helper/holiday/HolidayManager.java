package com.kaos.helper.holiday;

import java.util.Date;

import com.kaos.helper.holiday.entity.DayInfo;

public interface HolidayManager {
    /**
     * 获取节假日信息
     * 
     * @param date
     * @return
     */
    DayInfo getDayInfo(Date date);
}
