package com.kaos.skynet.api.data.his.mapper.inpatient.fee.balance.report;

import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.fee.balance.report.FinIpbDayReportDetail;

public interface FinIpbDayReportDetailMapper {
    /**
     * 查询日结记录
     * 
     * @param statNo   日结编号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param statCode 日结项目；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinIpbDayReportDetail queryDayReportDetail(String statNo, String statCode);

    /**
     * 查询日结明细
     * 
     * @param statNo 日结编号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    List<FinIpbDayReportDetail> queryDayReportDetails(String statNo);

    /**
     * 插入一条记录
     * 
     * @param finIpbDayReportDetail 日结明细记录
     * @return
     */
    Integer insertDayReportDetail(FinIpbDayReportDetail finIpbDayReportDetail);

    /**
     * 主键修改
     * 
     * @param statNo   日结编号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param statCode 日结项目；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param totCost  日结项目数值
     * @return
     */
    Integer updateDayReportDetail(FinIpbDayReportDetail finIpbDayReportDetail);
}
