package com.kaos.skynet.api.service.inf.inpatient.fee.report;

import java.util.Date;

import com.kaos.skynet.enums.common.DeptOwnEnum;

/**
 * 日结服务
 */
public interface ReportService {
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
}
