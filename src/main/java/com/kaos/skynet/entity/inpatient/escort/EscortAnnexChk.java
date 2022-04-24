package com.kaos.skynet.entity.inpatient.escort;

import java.time.LocalDateTime;

import com.kaos.skynet.entity.common.DawnOrgEmpl;

/**
 * 实体：附件审核（KAOS.ESCORT_ANNEX_CHK）
 */
public class EscortAnnexChk {
    /**
     * 附件id
     */
    public String annexNo = null;

    /**
     * 审核
     */
    public String chkEmplCode = null;

    /**
     * 审核日期
     */
    public LocalDateTime chkDate = null;

    /**
     * 阴性标识
     */
    public Boolean negativeFlag = null;

    /**
     * 检验日期
     */
    public LocalDateTime inspectDate = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：陪护证
         */
        public DawnOrgEmpl chkEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
