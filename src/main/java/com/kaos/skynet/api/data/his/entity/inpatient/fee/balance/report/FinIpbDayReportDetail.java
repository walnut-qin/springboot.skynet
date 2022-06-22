package com.kaos.skynet.api.data.his.entity.inpatient.fee.balance.report;

import com.google.common.base.Objects;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体：日结明细（XYHIS.FIN_IPB_DAYREPORTDETAIL）
 */
@Getter
@Setter
@Builder
public class FinIpbDayReportDetail {
    /**
     * 日结编号
     */
    private String statNo;

    /**
     * 日结项目
     */
    private String statCode;

    /**
     * 日结金额
     */
    private Double totCost;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIpbDayReportDetail) {
            var that = (FinIpbDayReportDetail) arg0;
            return StringUtils.equals(this.statNo, that.statNo)
                    && this.statCode == that.statCode;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(statNo, statCode);
    }
}
