package com.kaos.skynet.entity.pipe.lis;

import java.time.LocalDateTime;

/**
 * LIS检验结果（LISNEW.LIS_RESULT_NEW）
 */
public class LisResultNew {
    /**
     * 门诊号 / 住院号
     */
    public String patientId = null;

    /**
     * 项目编码
     */
    public String itemCode = null;

    /**
     * 项目名称
     */
    public String itemName = null;

    /**
     * 检验结果
     */
    public String result = null;

    /**
     * 检验时间
     */
    public LocalDateTime inspectDate = null;
}
