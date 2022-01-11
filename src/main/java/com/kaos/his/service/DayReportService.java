package com.kaos.his.service;

import java.util.Date;

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

    /**
     * 查询新医保统筹总额
     * 
     * @param balancer
     * @param beginDate
     * @param endDate
     * @return
     */
    Double queryNewYbPubCost(String balancer, Date beginDate, Date endDate);

    /**
     * 查询新医保账户总额
     * 
     * @param balancer
     * @param beginDate
     * @param endDate
     * @return
     */
    Double queryNewYbPayCost(String balancer, Date beginDate, Date endDate);
}
