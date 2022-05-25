package com.kaos.skynet.api.data.entity.inpatient.escort;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.api.enums.inpatient.escort.EscortActionEnum;
import com.kaos.skynet.core.utils.IntegerUtils;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EscortActionRec {
    /**
     * 陪护证编号
     */
    private String escortNo;

    /**
     * 状态序号
     */
    private Integer recNo;

    /**
     * 状态
     */
    private EscortActionEnum action;

    /**
     * 记录时间
     */
    private LocalDateTime recDate;

    /**
     * 备注
     */
    private String remark;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof EscortActionRec) {
            var that = (EscortActionRec) arg0;
            return StringUtils.equals(this.escortNo, that.escortNo) && IntegerUtils.equals(this.recNo, that.recNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(escortNo, recNo);
    }
}
