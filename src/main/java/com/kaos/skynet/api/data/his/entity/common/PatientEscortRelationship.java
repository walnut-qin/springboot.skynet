package com.kaos.skynet.api.data.his.entity.common;

import com.google.common.base.Objects;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 陪护关系实体
 */
@Getter
@Setter
@Builder
public class PatientEscortRelationship {
    /**
     * 患者卡号
     */
    private String patientCardNo;

    /**
     * 陪护卡号
     */
    private String escortCardNo;

    /**
     * 关系
     */
    private String relation;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof PatientEscortRelationship) {
            var that = (PatientEscortRelationship) arg0;
            return StringUtils.equals(this.patientCardNo, that.patientCardNo)
                && StringUtils.equals(this.escortCardNo, that.escortCardNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(patientCardNo, escortCardNo);
    }
}
