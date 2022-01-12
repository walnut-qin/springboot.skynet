package com.kaos.his.entity.inpatient.escort;

import java.util.Date;

import com.kaos.his.entity.common.Employee;

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
    public Date chkDate = null;

    /**
     * 阴性标识
     */
    public Boolean negativeFlag = null;

    /**
     * 检验日期
     */
    public Date inspectDate = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：陪护证
         */
        public Employee chkEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
