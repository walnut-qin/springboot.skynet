package com.kaos.skynet.api.data.his.entity.inpatient.fee.balance.report;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体：日结主表（XYHIS.FIN_IPB_DAYREPORT）
 */
@Getter
@Setter
@Builder
public class FinIpbDayReport {
    /**
     * 日结编号
     */
    private String statNo;

    /**
     * 开始时间
     */
    private LocalDateTime beginDate;

    /**
     * 结束时间
     */
    private LocalDateTime endDate;

    /**
     * 日结操作员编码
     */
    private String rptEmplCode;

    /**
     * 日结时间
     */
    private String rptOperDate;

    /**
     * 审核标志
     */
    private Boolean chkFlag;

    /**
     * 审核操作员
     */
    private String chkEmplCode;

    /**
     * 审核时间
     */
    private String chkDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIpbDayReport) {
            var that = (FinIpbDayReport) arg0;
            return StringUtils.equals(this.statNo, that.statNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(statNo);
    }
}
