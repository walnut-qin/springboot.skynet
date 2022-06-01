package com.kaos.skynet.api.data.entity.inpatient.surgery;

import com.kaos.skynet.api.data.enums.ValidEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 手术间信息（XYHIS.MET_OPS_ROOM）
 */
@Getter
@Setter
@Builder
public class MetOpsRoom {
    /**
     * 手术间ID
     */
    private String roomId;

    /**
     * 手术室名
     */
    private String roomName;

    /**
     * 助记码
     */
    private String inputCode;

    /**
     * 归属科室 {@link MetOpsRoom.AssociateEntity#dept}
     */
    private String deptCode;

    /**
     * 有效标识
     */
    private ValidEnum valid;

    /**
     * 操作员 {@link MetOpsRoom.AssociateEntity#operEmpl}
     */
    private String operCode;

    /**
     * 操作时间
     */
    private String operDate;
}
