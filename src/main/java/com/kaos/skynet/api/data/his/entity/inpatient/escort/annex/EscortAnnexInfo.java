package com.kaos.skynet.api.data.his.entity.inpatient.escort.annex;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EscortAnnexInfo {
    /**
     * 附件编码
     */
    private String annexNo;

    /**
     * 患者就诊卡号
     */
    private String cardNo;

    /**
     * 附件外链
     */
    private String annexUrl;

    /**
     * 记录日期
     */
    private LocalDateTime operDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof EscortAnnexInfo) {
            var that = (EscortAnnexInfo) arg0;
            return StringUtils.equals(this.annexNo, that.annexNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(annexNo);
    }
}
