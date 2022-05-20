package com.kaos.skynet.api.data.entity.pipe.lis;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * LIS检验结果（LISNEW.LIS_RESULT_NEW）
 */
@Data
public class LisResultNew {
    /**
     * 门诊号 / 住院号
     */
    private String patientId = null;

    /**
     * 项目编码
     */
    private String itemCode = null;

    /**
     * 项目名称
     */
    private String itemName = null;

    /**
     * 检验结果
     */
    private String result = null;

    /**
     * 检验时间
     */
    private LocalDateTime inspectDate = null;
}
