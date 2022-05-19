package com.kaos.skynet.api.entity.inpatient.surgery;

import com.kaos.skynet.api.entity.common.DawnOrgEmpl;
import com.kaos.skynet.enums.inpatient.surgery.SurgeryArrangeRoleEnum;

/**
 * 手术人员安排（XYHIS.MET_OPS_ARRANGE）
 */
public class MetOpsArrange {
    /**
     * 手术编号
     */
    public String operationNo = null;

    /**
     * 角色
     */
    public SurgeryArrangeRoleEnum role = null;

    /**
     * 职工编码
     */
    public String emplCode = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 手术申请数据
         */
        public MetOpsApply metOpsApply = null;

        /**
         * 实体：职员
         */
        public DawnOrgEmpl employee = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
