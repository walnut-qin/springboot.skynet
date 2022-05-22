package com.kaos.skynet.api.data.entity.inpatient;

import java.util.Date;

import com.kaos.skynet.api.enums.inpatient.FinIprPrepayInStateEnum;

/**
 * 实体：住院证（XYHIS.FIN_IPR_PREPAYIN）
 */
public class FinIprPrepayIn {
    /**
     * 就诊卡号
     */
    public String cardNo = null;

    /**
     * 住院证编号
     */
    public Integer happenNo = null;

    /**
     * 预约床位号
     */
    public String bedNo = null;

    /**
     * 预约医师编码
     */
    public String preDocCode = null;

    /**
     * 预约住院科室
     */
    public String preDeptCode = null;

    /**
     * 预约入院时间
     */
    public Date preDate = null;

    /**
     * 开立医师编码
     */
    public String openDocCode = null;

    /**
     * 开立时间
     */
    public Date openDate = null;

    /**
     * 住院证状态
     */
    public FinIprPrepayInStateEnum state = null;
}
