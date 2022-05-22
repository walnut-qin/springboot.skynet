package com.kaos.skynet.api.entity.inpatient;

import java.util.Date;

import com.kaos.skynet.api.data.entity.common.ComPatientInfo;
import com.kaos.skynet.api.data.entity.common.DawnOrgDept;
import com.kaos.skynet.api.data.entity.common.DawnOrgEmpl;
import com.kaos.skynet.api.data.entity.inpatient.ComBedInfo;
import com.kaos.skynet.api.data.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.entity.inpatient.escort.EscortVip;
import com.kaos.skynet.api.enums.inpatient.FinIprPrepayInStateEnum;

/**
 * 实体：住院证（XYHIS.FIN_IPR_PREPAYIN）
 */
public class FinIprPrepayIn {
    /**
     * 就诊卡号
     * {@link FinIprPrepayIn.AssociateEntity#patientInfo}
     * {@link FinIprPrepayIn.AssociateEntity#inMainInfo}
     */
    public String cardNo = null;

    /**
     * 住院证编号 {@link FinIprPrepayIn.AssociateEntity#inMainInfo}
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

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：患者信息（入院后应当更新为住院实体）
         */
        public ComPatientInfo patientInfo = null;

        /**
         * 住院实体 {@link FinIprPrepayIn#cardNo} + {@link FinIprPrepayIn#happenNo}
         */
        public FinIprInMainInfo inMainInfo = null;

        /**
         * 实体：床位信息
         */
        public ComBedInfo bedInfo = null;

        /**
         * 实体：预约医师
         */
        public DawnOrgEmpl preDoc = null;

        /**
         * 实体：预约科室
         */
        public DawnOrgDept preDept = null;

        /**
         * 实体：开立医师
         */
        public DawnOrgEmpl openDoc = null;

        /**
         * 实体：陪护证VIP
         */
        public EscortVip escortVip = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
