package com.kaos.skynet.entity.inpatient.surgery;

import com.kaos.skynet.entity.common.DawnOrgDept;
import com.kaos.skynet.entity.common.DawnOrgEmpl;
import com.kaos.skynet.enums.impl.common.ValidStateEnum;

/**
 * 手术间信息（XYHIS.MET_OPS_ROOM）
 */
public class MetOpsRoom {
    /**
     * 手术间ID
     */
    public String roomId = null;

    /**
     * 手术室名
     */
    public String roomName = null;

    /**
     * 助记码
     */
    public String inputCode = null;

    /**
     * 归属科室 {@link MetOpsRoom.AssociateEntity#dept}
     */
    public String deptCode = null;

    /**
     * 有效标识
     */
    public ValidStateEnum valid = null;

    /**
     * 操作员 {@link MetOpsRoom.AssociateEntity#operEmpl}
     */
    public String operCode = null;

    /**
     * 操作时间
     */
    public String operDate = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 归属科室 {@link MetOpsRoom#deptCode}
         */
        public DawnOrgDept dept = null;

        /**
         * 操作员 {@link MetOpsRoom#operCode}
         */
        public DawnOrgEmpl operEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
