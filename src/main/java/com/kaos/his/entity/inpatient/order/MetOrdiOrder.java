package com.kaos.his.entity.inpatient.order;

import java.util.Date;

import com.kaos.his.entity.common.Department;
import com.kaos.his.entity.common.Employee;
import com.kaos.his.entity.inpatient.Inpatient;
import com.kaos.his.enums.MetOrdiOrderStateEnum;

public class MetOrdiOrder {
    /**
     * 医嘱号
     */
    public String moOrder = null;

    /**
     * 住院流水号
     */
    public String inpatientNo = null;

    /**
     * 术语编码
     */
    public String termId = null;

    /**
     * 术语名称
     */
    public String termName = null;

    /**
     * 医嘱状态
     */
    public MetOrdiOrderStateEnum state = null;

    /**
     * 医嘱开立医生
     */
    public String moDocCode = null;

    /**
     * 医嘱开立时间
     */
    public Date moDate = null;

    /**
     * 医嘱执行科室
     */
    public String execDeptCode = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：住院患者
         */
        public Inpatient inpatient = null;

        /**
         * 开立医生
         */
        public Employee moDoc = null;

        /**
         * 执行科室
         */
        public Department execDept = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
