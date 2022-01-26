package com.kaos.his.entity.outpatient.fee;

import java.util.Date;

import com.kaos.his.entity.common.Department;
import com.kaos.his.entity.common.Employee;
import com.kaos.his.entity.outpatient.Outpatient;
import com.kaos.his.enums.common.TransTypeEnum;

/**
 * 门诊费用明细（XYHIS.FIN_OPB_FEEDETAIL）
 */
public class FinOpbFeeDetail {
    /**
     * 处方单号
     */
    public String recipeNo = null;

    /**
     * 处方内项目流水号
     */
    public Integer seqNo = null;

    /**
     * 业务类型
     */
    public TransTypeEnum transType = null;

    /**
     * 门诊号
     */
    public String clinicCode = null;

    /**
     * 项目编码
     */
    public String itemCode = null;

    /**
     * 项目名称
     */
    public String itemName = null;

    /**
     * 执行科室编码
     */
    public String execDeptCode = null;

    /**
     * 划价人编码
     */
    public String feeEmplCode = null;

    /**
     * 划价时间
     */
    public Date feeDate = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：门诊患者
         */
        public Outpatient outpatient = null;

        /**
         * 实体：执行科室
         */
        public Department execDept = null;

        /**
         * 实体：划价员
         */
        public Employee feeEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
