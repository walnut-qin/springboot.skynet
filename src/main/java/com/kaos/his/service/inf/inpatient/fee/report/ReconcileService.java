package com.kaos.his.service.inf.inpatient.fee.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kaos.his.entity.inpatient.fee.FinIpbFeeInfo;
import com.kaos.his.entity.inpatient.fee.FinIpbItemList;
import com.kaos.his.entity.inpatient.fee.FinIpbMedicineList;

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
}
