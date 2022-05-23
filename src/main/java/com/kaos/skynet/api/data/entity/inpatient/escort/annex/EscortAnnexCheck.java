package com.kaos.skynet.api.data.entity.inpatient.escort.annex;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 实体：附件审核（KAOS.ESCORT_ANNEX_CHK）
 */
@Data
public class EscortAnnexCheck {
    /**
     * 附件id
     */
    private String annexNo = null;

    /**
     * 审核
     */
    private String operCode = null;

    /**
     * 审核日期
     */
    private LocalDateTime operDate = null;

    /**
     * 阴性标识
     */
    private Boolean negative = null;

    /**
     * 检验日期
     */
    private LocalDateTime inspectDate = null;
}
