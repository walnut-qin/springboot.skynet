package com.kaos.skynet.api.data.entity.inpatient.surgery;

import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryArrangeRoleEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 手术人员安排（XYHIS.MET_OPS_ARRANGE）
 */
@Getter
@Setter
@Builder
public class MetOpsArrange {
    /**
     * 手术编号
     */
    private String operationNo;

    /**
     * 角色
     */
    private SurgeryArrangeRoleEnum role;

    /**
     * 职工编码
     */
    private String emplCode;
}
