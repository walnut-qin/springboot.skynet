package com.kaos.skynet.api.data.his.entity.inpatient.surgery;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
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
     * 手术等级
     */
    private SurgeryLevelEnum surgeryLevel;

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

    /**
     * 手术等级
     */
    @Getter
    @AllArgsConstructor
    public enum SurgeryLevelEnum implements Enum {
        一级("1", "一级"),
        二级("2", "二级"),
        三级("3", "三级"),
        四级("4", "四级");

        private String value;

        private String description;
    }
}
