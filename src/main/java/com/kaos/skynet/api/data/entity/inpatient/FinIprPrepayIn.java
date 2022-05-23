package com.kaos.skynet.api.data.entity.inpatient;

import java.util.Date;

import com.kaos.skynet.api.enums.inpatient.FinIprPrepayInStateEnum;

import lombok.Data;

/**
 * 实体：住院证（XYHIS.FIN_IPR_PREPAYIN）
 */
@Data
public class FinIprPrepayIn {
    /**
     * 就诊卡号
     */
    private String cardNo = null;

    /**
     * 住院证编号
     */
    private Integer happenNo = null;

    /**
     * 预约床位号
     */
    private String bedNo = null;

    /**
     * 预约医师编码
     */
    private String preDocCode = null;

    /**
     * 预约住院科室
     */
    private String preDeptCode = null;

    /**
     * 预约入院时间
     */
    private Date preDate = null;

    /**
     * 开立医师编码
     */
    private String openDocCode = null;

    /**
     * 开立时间
     */
    private Date openDate = null;

    /**
     * 住院证状态
     */
    private FinIprPrepayInStateEnum state = null;
}
