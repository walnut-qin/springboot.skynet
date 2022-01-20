package com.kaos.his.service.inpatient;

import java.util.Date;

import com.kaos.his.enums.DeptOwnEnum;

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
     * 修复新医保日结明细数据
     * 
     * @param statNo
     */
    void fixNewYbDayReportData(Date beginDate, Date endDate, DeptOwnEnum deptOwn);

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

    /**
     * 查询汇总改数据
     * 
     * @param beginDate
     * @param endDate
     * @param deptOwn
     */
    void exportNewYbData(Date beginDate, Date endDate, DeptOwnEnum deptOwn);
}
