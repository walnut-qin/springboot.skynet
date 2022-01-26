package com.kaos.his.entity.inpatient;

import com.kaos.his.entity.common.Department;
import com.kaos.his.enums.common.ValidStateEnum;
import com.kaos.his.enums.inpatient.BedStateEnum;

/**
 * 床位信息（XYHIS.COM_BEDINFO）
 */
public class ComBedInfo {
    /**
     * 床号
     */
    public String bedNo = null;

    /**
     * 归属病区
     */
    public String nurseCellCode = null;

    /**
     * 床位状态
     */
    public BedStateEnum state = null;

    /**
     * 有效标识
     */
    public ValidStateEnum valid = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 基本信息操作员
         */
        public Department nurseCell = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();

    /**
     * 获取去除病区的格式的床位号
     */
    public String getBriefBedNo() {
        return this.bedNo.replaceFirst("^" + this.nurseCellCode, "");
    }
}
