package com.kaos.skynet.api.entity.inpatient.escort;

import java.util.List;

import com.kaos.skynet.api.entity.common.ComPatientInfo;
import com.kaos.skynet.api.entity.inpatient.FinIprPrepayIn;

/**
 * 实体：陪护证主表（KAOS.ESCORT_MAIN_INFO）
 */
public class EscortMainInfo {
    /**
     * 陪护证编号
     */
    public String escortNo = null;

    /**
     * 患者卡号
     */
    public String patientCardNo = null;

    /**
     * 
     */
    public Integer happenNo = null;

    /**
     * 陪护人卡号
     */
    public String helperCardNo = null;

    /**
     * 备注
     */
    public String remark = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：住院证
         */
        public FinIprPrepayIn prepayIn = null;

        /**
         * 实体：陪护人
         */
        public ComPatientInfo helper = null;

        /**
         * 状态列表
         */
        public List<EscortStateRec> stateRecs = null;

        /**
         * 行为列表
         */
        public List<EscortActionRec> actionRecs = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
