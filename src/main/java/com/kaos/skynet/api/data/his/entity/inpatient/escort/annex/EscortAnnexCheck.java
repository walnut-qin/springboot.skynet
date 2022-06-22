package com.kaos.skynet.api.data.his.entity.inpatient.escort.annex;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.Entity;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体：附件审核（KAOS.ESCORT_ANNEX_CHK）
 */
@Getter
@Setter
@Builder
public class EscortAnnexCheck extends Entity {
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
            var that = (EscortAnnexCheck) arg0;
            return StringUtils.equals(this.annexNo, that.annexNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(annexNo);
    }
}
