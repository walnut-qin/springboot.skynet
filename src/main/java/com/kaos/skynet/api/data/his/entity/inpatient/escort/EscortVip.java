package com.kaos.skynet.api.data.his.entity.inpatient.escort;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.utils.IntegerUtils;

import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EscortVip {
    /**
     * 实体：患者卡号
     */
    private String patientCardNo;

    /**
     * 住院证序号
     */
    private Integer happenNo;

    /**
     * 陪护人卡号（VIP）
     */
    private String helperCardNo;

    /**
     * 记录日期
     */
    private LocalDateTime recDate;

    /**
     * 备注
     */
    private String remark;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof EscortVip) {
            var that = (EscortVip) arg0;
            return StringUtils.equals(this.patientCardNo, that.patientCardNo)
                    && IntegerUtils.equals(this.happenNo, that.happenNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(patientCardNo, happenNo);
    }
}
