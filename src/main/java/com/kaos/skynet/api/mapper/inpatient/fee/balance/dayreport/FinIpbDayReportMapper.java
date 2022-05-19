package com.kaos.skynet.api.mapper.inpatient.fee.balance.dayreport;

import java.util.Date;
import java.util.List;

import com.kaos.skynet.api.entity.inpatient.fee.balance.dayreport.FinIpbDayReport;
import com.kaos.skynet.enums.common.DeptOwnEnum;

public interface FinIpbDayReportMapper {
    /**
     * 主键查询
     * 
     * @param statNo 日结编号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinIpbDayReport queryDayReport(String statNo);

    /**
     * 指定院区查询某个时段内的日结
     * 
     * @param beginDate 开始时间；等于 {@code null} 时，不作为判断条件
     * @param endDate   结束时间；等于 {@code null} 时，不作为判断条件
     * @param deptOwn   院区；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<FinIpbDayReport> queryDayReprots(Date beginDate, Date endDate, DeptOwnEnum deptOwn);
}
