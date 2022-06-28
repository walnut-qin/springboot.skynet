package com.kaos.skynet.api.data.his.entity.inpatient.surgery;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;

/**
 * 手术人员授权
 */
@Getter
@Builder
public class SurgeryEmplPriv {
    /**
     * 手术编码
     */
    private String icdCode;

    /**
     * 职员编码
     */
    private String emplCode;

    /**
     * 有效性标识
     */
    private Boolean valid;

    /**
     * 操作时间
     */
    private LocalDateTime operDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof SurgeryEmplPriv) {
            var that = (SurgeryEmplPriv) arg0;
            return StringUtils.equals(this.icdCode, that.icdCode)
                    && StringUtils.equals(this.emplCode, that.emplCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.icdCode, this.emplCode);
    }
}
