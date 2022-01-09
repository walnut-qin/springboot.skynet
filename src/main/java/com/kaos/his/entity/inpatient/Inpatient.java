package com.kaos.his.entity.inpatient;

import java.util.Date;

import com.kaos.his.entity.common.Bed;
import com.kaos.his.entity.common.Department;
import com.kaos.his.entity.common.Employee;
import com.kaos.his.entity.common.Patient;

/**
 * 实体：住院患者（FIN_IPR_INMAININFO）
 */
public class Inpatient extends Patient {
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
    public String deptCode = null;

    /**
     * 入院日期
     */
    public Date inDate = null;

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
     * 关联实体
     */
    public class AssociateEntity extends Patient.AssociateEntity {
        /**
         * 实体：住院科室
         */
        public Department stayedDept = null;

        /**
         * 实体：床位
         */
        public Bed bed = null;

        /**
         * 实体：住院病区
         */
        public Department stayedNurseCell = null;

        /**
         * 实体：住院医师
         */
        public Employee houseDoctor = null;

        /**
         * 实体：主治医师
         */
        public Employee chargeDoctor = null;
    }

    /**
     * 关联实体
     */
    public AssociateEntity associateEntity = new AssociateEntity();
}
