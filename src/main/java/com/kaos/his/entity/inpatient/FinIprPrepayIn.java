package com.kaos.his.entity.inpatient;

import java.util.Date;

import com.kaos.his.entity.common.Department;
import com.kaos.his.entity.common.Employee;
import com.kaos.his.entity.common.Patient;
import com.kaos.his.entity.inpatient.escort.EscortVip;
import com.kaos.his.enums.inpatient.FinIprPrepayInStateEnum;

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

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：患者信息（入院后应当更新为住院实体）
         */
        public Patient patient = null;

        /**
         * 实体：床位信息
         */
        public ComBedInfo bedInfo = null;

        /**
         * 实体：预约医师
         */
        public Employee preDoc = null;

        /**
         * 实体：预约科室
         */
        public Department preDept = null;

        /**
         * 实体：开立医师
         */
        public Employee openDoc = null;

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
