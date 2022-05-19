package com.kaos.skynet.entity.inpatient.escort;

import java.util.Date;

import com.kaos.skynet.enums.inpatient.escort.EscortActionEnum;

public class EscortActionRec {
    /**
     * 陪护证编号
     */
    public String escortNo = null;

    /**
     * 状态序号
     */
    public Integer recNo = null;

    /**
     * 状态
     */
    public EscortActionEnum action = null;

    /**
     * 记录时间
     */
    public Date recDate = null;

    /**
     * 备注
     */
    public String remark = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：陪护证
         */
        public EscortMainInfo escortMainInfo = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
