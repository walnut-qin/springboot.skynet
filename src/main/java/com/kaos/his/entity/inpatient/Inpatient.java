package com.kaos.his.entity.inpatient;

import java.util.Date;

import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.his.entity.common.DawnOrgEmpl;
import com.kaos.his.entity.common.ComPatientInfo;
import com.kaos.his.enums.inpatient.InSourceEnum;
import com.kaos.his.enums.inpatient.InStateEnum;

/**
 * 实体：住院患者（FIN_IPR_INMAININFO）
 */
public class Inpatient extends ComPatientInfo {
    /**
     * 住院流水号
     */
    public String inpatientNo = null;

    /**
     * 住院号
     */
    public String patientNo = null;

    /**
     * 住院科室编码
     */
    public String stayedDeptCode = null;

    /**
     * 床号
     */
    public String bedNo = null;

    /**
     * 住院病区编码
     */
    public String nurseCellCode = null;

    /**
     * 住院医师编码
     */
    public String houseDocCode = null;

    /**
     * 主治医师编码
     */
    public String chargeDocCode = null;

    /**
     * 主任医师编码
     */
    public String chiefDocCode = null;

    /**
     * 责任护士编码
     */
    public String dutyNurseCode = null;

    /**
     * 入院来源
     */
    public InSourceEnum inSource = null;

    /**
     * 在院状态
     */
    public InStateEnum inState = null;

    /**
     * 入院日期
     */
    public Date inDate = null;

    /**
     * 出院日期
     */
    public Date outDate = null;

    /**
     * ERAS标志
     */
    public Boolean erasFlag = null;

    /**
     * VTE等级
     */
    public String vteRank = null;

    /**
     * 住院证关联
     */
    public Integer happenNo = null;

    /**
     * 关联实体
     */
    public class AssociateEntity extends ComPatientInfo.AssociateEntity {
        /**
         * 实体：住院科室
         */
        public DawnOrgDept stayedDept = null;

        /**
         * 实体：床位
         */
        public ComBedInfo bed = null;

        /**
         * 实体：住院病区
         */
        public DawnOrgDept stayedNurseCell = null;

        /**
         * 实体：住院医师
         */
        public DawnOrgEmpl houseDoctor = null;

        /**
         * 实体：主治医师
         */
        public DawnOrgEmpl chargeDoctor = null;

        /**
         * 实体：主任医师
         */
        public DawnOrgEmpl chiefDoctor = null;

        /**
         * 实体：责任护士
         */
        public DawnOrgEmpl dutyNurse = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
