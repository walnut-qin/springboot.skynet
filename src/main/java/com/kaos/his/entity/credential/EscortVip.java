package com.kaos.his.entity.credential;

import java.util.Date;

/**
 * 陪护证VIP记录
 */
public class EscortVip {
    /**
     * 患者就诊卡号
     */
    public String patientCardNo = null;

    /**
     * 住院证编号
     */
    public Integer happenNo = null;

    /**
     * 陪护人卡号
     */
    public String helperCardNo = null;

    /**
     * 记录添加日期
     */
    public Date operDate = null;

    /**
     * 备注
     */
    public String remark = null;
}
