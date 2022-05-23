package com.kaos.skynet.api.data.entity.inpatient.escort;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EscortVip {
    /**
     * 实体：患者卡号
     */
    private String patientCardNo = null;

    /**
     * 住院证序号
     */
    private Integer happenNo = null;

    /**
     * 陪护人卡号（VIP）
     */
    private String helperCardNo = null;

    /**
     * 记录日期
     */
    private LocalDateTime recDate = null;

    /**
     * 备注
     */
    private String remark = null;
}
