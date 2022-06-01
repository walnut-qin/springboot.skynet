package com.kaos.skynet.api.data.entity.inpatient.surgery;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 手术记录（XYHIS.MET_OPS_OPERATIONITEM）
 */
@Getter
@Setter
@Builder
public class MetOpsItem {
    /**
     * 手术编号
     */
    private String operationNo;

    /**
     * 项目编码
     */
    private String itemCode;

    /**
     * 项目名称
     */
    private String itemName;
}
