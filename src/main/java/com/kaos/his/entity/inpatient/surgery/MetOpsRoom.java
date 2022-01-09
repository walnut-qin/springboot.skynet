package com.kaos.his.entity.inpatient.surgery;

import com.kaos.his.entity.common.Department;
import com.kaos.his.enums.ValidStateEnum;

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
     * 归属科室
     */
    public String deptCode = null;

    /**
     * 有效标识
     */
    public ValidStateEnum valid = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 归属科室
         */
        public Department department = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
