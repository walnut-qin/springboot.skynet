package com.kaos.skynet.api.data.entity.inpatient.escort;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.api.enums.inpatient.escort.EscortStateEnum;
import com.kaos.skynet.core.type.utils.IntegerUtils;

import org.apache.commons.lang3.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体：陪护证状态（KAOS.ESCORT_STATE_REC）
 */
@Getter
@Setter
@Builder
public class EscortStateRec {
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
    private EscortStateEnum state;

    /**
     * 记录员编码
     */
    private String recEmplCode;

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
        if (arg0 instanceof EscortStateRec) {
            var that = (EscortStateRec) arg0;
            return StringUtils.equals(this.escortNo, that.escortNo) && IntegerUtils.equals(this.recNo, that.recNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(escortNo, recNo);
    }
}
