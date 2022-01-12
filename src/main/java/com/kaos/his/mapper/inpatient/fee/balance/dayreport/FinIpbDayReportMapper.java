package com.kaos.his.mapper.inpatient.fee.balance.dayreport;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.inpatient.fee.balance.dayreport.FinIpbDayReport;
import com.kaos.his.enums.DeptOwnEnum;

public interface FinIpbDayReportMapper {
    /**
     * 主键查询
     * 
     * @param statNo
     * @return
     */
    FinIpbDayReport queryDayReport(String statNo);

    /**
     * 指定院区查询某个时段内的日结
     * 
     * @param beginDate
     * @param endDate
     * @param deptOwn
     * @return
     */
    List<FinIpbDayReport> queryDayReprots(Date beginDate, Date endDate, DeptOwnEnum deptOwn);
}
