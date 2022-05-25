package com.kaos.skynet.api.data.entity.pipe.lis;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * LIS检验结果（LISNEW.LIS_RESULT_NEW）
 */
@Getter
@Setter
@Builder
public class LisResultNew {
    /**
     * 门诊号 / 住院号
     */
    private String patientId;

    /**
     * 项目编码
     */
    private String itemCode;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 检验结果
     */
    private String result;

    /**
     * 检验时间
     */
    private LocalDateTime inspectDate;
}
