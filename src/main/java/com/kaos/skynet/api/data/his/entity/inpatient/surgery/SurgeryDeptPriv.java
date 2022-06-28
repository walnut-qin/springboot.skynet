package com.kaos.skynet.api.data.his.entity.inpatient.surgery;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;

/**
 * 手术科室权限表
 */
@Getter
@Builder
public class SurgeryDeptPriv {
    /**
     * 手术编码
     */
    private String icdCode;

    /**
     * 科室名称
     */
    private String deptCode;

    /**
     * 授权有效性
     */
    private Boolean valid;

    /**
     * 操作时间
     */
    private LocalDateTime operDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof SurgeryDeptPriv) {
            var that = (SurgeryDeptPriv) arg0;
            return StringUtils.equals(this.icdCode, that.icdCode)
                    && StringUtils.equals(this.deptCode, that.deptCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.icdCode, this.deptCode);
    }
}
