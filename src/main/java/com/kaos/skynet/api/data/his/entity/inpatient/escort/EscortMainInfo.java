package com.kaos.skynet.api.data.his.entity.inpatient.escort;

import com.google.common.base.Objects;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体：陪护证主表（KAOS.ESCORT_MAIN_INFO）
 */
@Getter
@Setter
@Builder
public class EscortMainInfo {
    /**
     * 陪护证编号
     */
    private String escortNo;

    /**
     * 患者卡号
     */
    private String patientCardNo;

    /**
     * 
     */
    private Integer happenNo;

    /**
     * 陪护人卡号
     */
    private String helperCardNo;

    /**
     * 备注
     */
    private String remark;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof EscortMainInfo) {
            var that = (EscortMainInfo) arg0;
            return StringUtils.equals(this.escortNo, that.escortNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(escortNo);
    }
}
