package com.kaos.his.entity.common;

import com.kaos.his.enums.BedStateEnum;

/**
 * 床位信息
 */
public class Bed {
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
    public AssociateEntity associateEntity = new AssociateEntity();

    /**
     * 获取去除病区的格式的床位号
     */
    public String getBriefBedNo() {
        return this.bedNo.replaceFirst("^" + this.nurseCellCode, "");
    }
}
