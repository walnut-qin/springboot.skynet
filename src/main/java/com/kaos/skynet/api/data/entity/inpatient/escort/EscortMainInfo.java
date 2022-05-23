package com.kaos.skynet.api.data.entity.inpatient.escort;

import lombok.Data;

/**
 * 实体：陪护证主表（KAOS.ESCORT_MAIN_INFO）
 */
@Data
public class EscortMainInfo {
    /**
     * 陪护证编号
     */
    private String escortNo = null;

    /**
     * 患者卡号
     */
    private String patientCardNo = null;

    /**
     * 
     */
    private Integer happenNo = null;

    /**
     * 陪护人卡号
     */
    private String helperCardNo = null;

    /**
     * 备注
     */
    private String remark = null;
}
