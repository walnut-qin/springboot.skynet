package com.kaos.his.entity.inpatient.escort;

import java.util.Date;

import com.kaos.his.entity.common.DawnOrgEmpl;
import com.kaos.his.enums.inpatient.escort.EscortStateEnum;

/**
 * 实体：陪护证状态（KAOS.ESCORT_STATE_REC）
 */
public class EscortStateRec {
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
    public EscortStateEnum state = null;

    /**
     * 记录员编码
     */
    public String recEmplCode = null;

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

        /**
         * 实体：记录员
         */
        public DawnOrgEmpl recEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
