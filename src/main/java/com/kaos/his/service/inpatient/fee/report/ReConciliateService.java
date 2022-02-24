package com.kaos.his.service.inpatient.fee.report;

import java.util.Date;

/**
 * 对账服务(HIS和用友系统对账)
 */
public interface ReConciliateService {
    /**
     * 核对住院收入，按照科室对账
     * 
     * @param beginDate 开始时间
     * @param endDate   结束时间
     */
    void checkInpatientIncome(Date beginDate, Date endDate);
}
