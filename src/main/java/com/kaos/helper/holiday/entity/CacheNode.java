package com.kaos.helper.holiday.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * cache 节点
 */
public class CacheNode implements Serializable {
    /**
     * 日期信息
     */
    public DayInfo dayInfo = null;

    /**
     * 有效期至
     */
    public Date validDate = null;
}
