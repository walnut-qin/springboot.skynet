package com.kaos.skynet.api.data.entity.inpatient.escort;

import java.time.LocalDateTime;

import com.kaos.skynet.api.enums.inpatient.escort.EscortStateEnum;

import lombok.Data;

/**
 * 实体：陪护证状态（KAOS.ESCORT_STATE_REC）
 */
@Data
public class EscortStateRec {
    /**
     * 陪护证编号
     */
    private String escortNo = null;

    /**
     * 状态序号
     */
    private Integer recNo = null;

    /**
     * 状态
     */
    private EscortStateEnum state = null;

    /**
     * 记录员编码
     */
    private String recEmplCode = null;

    /**
     * 记录时间
     */
    private LocalDateTime recDate = null;

    /**
     * 备注
     */
    private String remark = null;
}
