package com.kaos.his.entity.common;

import com.kaos.his.enums.impl.common.DeptOwnEnum;
import com.kaos.his.enums.impl.common.ValidStateEnum;

/**
 * 实体：科室信息（XYHIS.DAWN_ORG_DEPT）
 */
public class DawnOrgDept {
    /**
     * 科室编码
     */
    public String deptCode;

    /**
     * 科室名称
     */
    public String deptName;

    /**
     * 院区
     */
    public DeptOwnEnum deptOwn;

    /**
     * 有效标识
     */
    public ValidStateEnum valid;
}
