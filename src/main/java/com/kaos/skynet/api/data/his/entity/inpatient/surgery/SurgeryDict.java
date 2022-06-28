package com.kaos.skynet.api.data.his.entity.inpatient.surgery;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SurgeryDict {
    /**
     * 国家ICD编码
     */
    private String icdCode;

    /**
     * 手术名称
     */
    private String surgeryName;

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
        if (arg0 instanceof SurgeryDict) {
            var that = (SurgeryDict) arg0;
            return StringUtils.equals(this.icdCode, that.icdCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.icdCode);
    }
}
