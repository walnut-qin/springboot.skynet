package com.kaos.his.service.inf.inpatient.fee.report;

import java.util.Date;

import com.google.common.collect.Multimap;
import com.kaos.his.entity.inpatient.fee.balance.FinIpbBalanceHead;
import com.kaos.his.enums.impl.common.DeptOwnEnum;

import org.javatuples.Pair;
import org.javatuples.Triplet;

/**
 * 日结服务
 */
public interface DayReportService {
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

    /**
     * 导出汇总改数据
     * 
     * @param beginDate
     * @param endDate
     * @param deptOwn
     * @return [{结算序号 -> 结算实体}, [新医保统筹, 新医保账户], [非新医保统筹, 非新医保账户]}]
     */
    Triplet<Multimap<String, FinIpbBalanceHead>, Pair<Double, Double>, Pair<Double, Double>> exportNewYbData(
            Date beginDate, Date endDate, DeptOwnEnum deptOwn);
}
