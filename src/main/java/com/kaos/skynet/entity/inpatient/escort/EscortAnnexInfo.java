package com.kaos.skynet.entity.inpatient.escort;

import java.util.Date;

import com.kaos.skynet.entity.common.ComPatientInfo;

public class EscortAnnexInfo {
    /**
     * 附件编码
     */
    public String annexNo = null;

    /**
     * 患者就诊卡号
     */
    public String cardNo = null;

    /**
     * 附件外链
     */
    public String annexUrl = null;

    /**
     * 记录日期
     */
    public Date recDate = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：患者信息
         */
        public ComPatientInfo patientInfo = null;

        /**
         * 审核记录
         */
        public EscortAnnexChk escortAnnexChk = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}