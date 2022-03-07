package com.kaos.skynet.entity.inpatient.fee.balance.dayreport;

/**
 * 实体：日结明细（XYHIS.FIN_IPB_DAYREPORTDETAIL）
 */
public class FinIpbDayReportDetail {
    /**
     * 日结编号
     */
    public String statNo = null;

    /**
     * 日结项目
     */
    public String statCode = null;

    /**
     * 日结金额
     */
    public Double totCost = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：日结记录
         */
        public FinIpbDayReport finIpbDayReport = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
