package com.kaos.helper.holiday;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.kaos.helper.holiday.entity.CacheNode;
import com.kaos.helper.holiday.entity.DayInfo;

public interface HolidayHelper {
    /**
     * 获取节假日信息
     * 
     * @param date
     * @return
     */
    DayInfo getDayInfo(Date date);

    /**
     * 获取cache的一份拷贝，用于展示
     * 
     * @return
     */
    ConcurrentHashMap<String, CacheNode> getCache();
}
