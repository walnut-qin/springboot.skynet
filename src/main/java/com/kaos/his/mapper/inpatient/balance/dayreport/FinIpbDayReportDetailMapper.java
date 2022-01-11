package com.kaos.his.mapper.inpatient.balance.dayreport;

import java.util.List;

import com.kaos.his.entity.inpatient.balance.dayreport.FinIpbDayReportDetail;

public interface FinIpbDayReportDetailMapper {
    /**
     * 主键查询
     * 
     * @param statNo
     * @return
     */
    FinIpbDayReportDetail queryDayReportDetail(String statNo, String statCode);

    /**
     * 查询日结明细
     * 
     * @param statNo
     * @return
     */
    List<FinIpbDayReportDetail> queryDayReportDetails(String statNo);
}
