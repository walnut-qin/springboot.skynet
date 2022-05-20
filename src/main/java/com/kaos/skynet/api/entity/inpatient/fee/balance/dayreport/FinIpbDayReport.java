package com.kaos.skynet.api.entity.inpatient.fee.balance.dayreport;

import java.util.Date;

import com.kaos.skynet.api.data.entity.common.DawnOrgEmpl;

/**
 * 实体：日结主表（XYHIS.FIN_IPB_DAYREPORT）
 */
public class FinIpbDayReport {
    /**
     * 日结编号
     */
    public String statNo = null;

    /**
     * 开始时间
     */
    public Date beginDate = null;

    /**
     * 结束时间
     */
    public Date endDate = null;

    /**
     * 日结操作员编码
     */
    public String rptEmplCode = null;

    /**
     * 日结时间
     */
    public String rptOperDate = null;

    /**
     * 审核标志
     */
    public Boolean chkFlag = null;

    /**
     * 审核操作员
     */
    public String chkEmplCode = null;

    /**
     * 审核时间
     */
    public String chkDate = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 日结操作员
         */
        public DawnOrgEmpl rptEmployee = null;

        /**
         * 审核员
         */
        public DawnOrgEmpl chkEmployee = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
