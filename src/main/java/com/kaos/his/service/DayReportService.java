package com.kaos.his.service;

/**
 * 日结服务
 */
public interface DayReportService {
    /**
     * 修复新医保日结明细数据
     * 
     * @param statNo
     */
    void fixNewYbDayReportData(String statNo);
}
