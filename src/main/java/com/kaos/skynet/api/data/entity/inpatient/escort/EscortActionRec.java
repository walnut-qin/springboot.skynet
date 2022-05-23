package com.kaos.skynet.api.data.entity.inpatient.escort;

import java.time.LocalDateTime;

import com.kaos.skynet.api.enums.inpatient.escort.EscortActionEnum;

import lombok.Data;

@Data
public class EscortActionRec {
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
    private EscortActionEnum action = null;

    /**
     * 记录时间
     */
    private LocalDateTime recDate = null;

    /**
     * 备注
     */
    private String remark = null;
}
