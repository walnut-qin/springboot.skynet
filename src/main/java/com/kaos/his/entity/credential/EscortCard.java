package com.kaos.his.entity.credential;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.personnel.Patient;

public class EscortCard {
    /**
     * 陪护证编号
     */
    public String escortNo;

    /**
     * 患者就诊卡号
     */
    public String patientCardNo = null;

    /**
     * 住院证序号
     */
    public Integer happenNo = null;

    /**
     * 陪护人卡号
     */
    public String helperCardNo = null;

    /**
     * 添加记录的时间戳
     */
    public Date operDate = null;

    /**
     * 备注
     */
    public String remark = null;

    /**
     * 关联的住院证
     */
    public PreinCard preinCard;

    /**
     * 关联的陪护人
     */
    public Patient helper;

    /**
     * 状态清单
     */
    public List<EscortCardState> states;

    /**
     * 行为清单
     */
    public List<EscortCardAction> actions;
}
