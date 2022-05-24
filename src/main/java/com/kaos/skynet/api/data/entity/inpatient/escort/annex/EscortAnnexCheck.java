package com.kaos.skynet.api.data.entity.inpatient.escort.annex;

import java.time.LocalDateTime;

import com.google.common.base.Objects;

import lombok.Builder;
import lombok.Data;

/**
 * 实体：附件审核（KAOS.ESCORT_ANNEX_CHK）
 */
@Data
@Builder
public class EscortAnnexCheck {
    /**
     * 附件id
     */
    private String annexNo;

    /**
     * 审核
     */
    private String operCode;

    /**
     * 审核日期
     */
    private LocalDateTime operDate;

    /**
     * 阴性标识
     */
    private Boolean negative;

    /**
     * 检验日期
     */
    private LocalDateTime inspectDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof EscortAnnexCheck) {
            return annexNo.equals(((EscortAnnexCheck) arg0).annexNo);
        }
        return super.equals(arg0);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(annexNo);
    }
}
