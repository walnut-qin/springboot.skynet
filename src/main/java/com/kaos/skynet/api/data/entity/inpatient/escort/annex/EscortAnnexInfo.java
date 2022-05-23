package com.kaos.skynet.api.data.entity.inpatient.escort.annex;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EscortAnnexInfo {
    /**
     * 附件编码
     */
    private String annexNo = null;

    /**
     * 患者就诊卡号
     */
    private String cardNo = null;

    /**
     * 附件外链
     */
    private String annexUrl = null;

    /**
     * 记录日期
     */
    private LocalDateTime operDate = null;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EscortAnnexInfo) {
            EscortAnnexInfo info = (EscortAnnexInfo) obj;
            return annexNo.equals(info.annexNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return annexNo.hashCode();
    }
}
