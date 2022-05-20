package com.kaos.skynet.api.data.entity.common;

import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;
import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 实体：科室信息（XYHIS.DAWN_ORG_DEPT）
 */
@Data
public class DawnOrgDept {
    /**
     * 科室编码
     */
    private String deptCode;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 科室类型
     */
    private DeptTypeEnum deptType;

    /**
     * 院区
     */
    private DeptOwnEnum deptOwn;

    /**
     * 有效标识
     */
    private ValidEnum valid;

    /**
     * 科室类型
     */
    @AllArgsConstructor
    public static enum DeptTypeEnum implements Enum {
        门诊("C", "门诊"),
        住院("I", "住院"),
        财务("F", "财务"),
        后勤("L", "后勤"),
        药库("PI", "药库"),
        医技("T", "医技"),
        其它("O", "其它"),
        机关("D", "机关"),
        药房("P", "药房"),
        护士站("N", "护士站");
    
        /**
         * 数据库存值
         */
        @Getter
        private String value;
    
        /**
         * 描述存值
         */
        @Getter
        private String description;
    }
}
