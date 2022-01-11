package com.kaos.his.mapper.inpatient.balance.dayreport;

import com.kaos.his.entity.inpatient.balance.dayreport.FinIpbDayReport;

public interface FinIpbDayReportMapper {
    /**
     * 主键查询
     * 
     * @param statNo
     * @return
     */
    FinIpbDayReport queryDayReport(String statNo);
}
