package com.kaos.skynet.api.data.his.mapper.inpatient.fee.balance.report;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.fee.balance.report.FinIpbDayReport;
import com.kaos.skynet.api.data.his.enums.DeptOwnEnum;

import lombok.Builder;
import lombok.Getter;

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
     * @return
     */
    List<FinIpbDayReport> queryDayReprots(Key key);

    @Getter
    @Builder
    public static class Key {
        /**
         * 院区
         */
        private DeptOwnEnum deptOwn;

        /**
         * 日结开始时间
         */
        private LocalDateTime beginRptDate;

        /**
         * 日结结束时间
         */
        private LocalDateTime endRptDate;
    }
}
