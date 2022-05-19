package com.kaos.skynet.api.service.inf.inpatient.fee.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;
import com.kaos.skynet.api.entity.inpatient.fee.FinIpbFeeInfo;
import com.kaos.skynet.api.entity.inpatient.fee.FinIpbItemList;
import com.kaos.skynet.api.entity.inpatient.fee.FinIpbMedicineList;
import com.kaos.skynet.api.entity.inpatient.fee.balance.FinIpbBalanceHead;
import com.kaos.skynet.api.enums.common.DeptOwnEnum;

import org.javatuples.Pair;
import org.javatuples.Triplet;

/**
 * 对账服务(HIS和用友系统对账)
 */
public interface ReconcileService {
    /**
     * 核对住院收入，按照科室对账
     * 科室 -> { 处方号 -> { 费用信息, 非药品信息, 药品信息 } }
     * 
     * @param beginDate 开始时间
     * @param endDate   结束时间
     */
    Map<String, Map<String, Triplet<List<FinIpbFeeInfo>, List<FinIpbItemList>, List<FinIpbMedicineList>>>> checkInpatientIncome(
            Date beginDate, Date endDate);

    /**
     * 导出汇总改数据
     * 
     * @param beginDate
     * @param endDate
     * @param deptOwn
     * @return { 医保类型 -> < <统筹, 账户>, {结算序号 -> 结算实体} > }
     */
    Map<String, Pair<Pair<Double, Double>, Multimap<String, FinIpbBalanceHead>>> exportNewYbData(Date beginDate,
            Date endDate, DeptOwnEnum deptOwn);
}
