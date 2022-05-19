package com.kaos.skynet.api.entity.common;

import com.kaos.skynet.enums.common.DeptOwnEnum;
import com.kaos.skynet.enums.common.DeptTypeEnum;
import com.kaos.skynet.enums.common.ValidStateEnum;

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
     * 科室类型
     */
    public DeptTypeEnum deptType;

    /**
     * 院区
     */
    public DeptOwnEnum deptOwn;

    /**
     * 有效标识
     */
    public ValidStateEnum valid;
}
