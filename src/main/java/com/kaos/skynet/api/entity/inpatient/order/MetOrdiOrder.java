package com.kaos.skynet.api.entity.inpatient.order;

import java.util.Date;

import com.kaos.skynet.api.data.entity.common.DawnOrgDept;
import com.kaos.skynet.api.data.entity.common.DawnOrgEmpl;
import com.kaos.skynet.api.enums.inpatient.order.MetOrdiOrderStateEnum;

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
         * 开立医生
         */
        public DawnOrgEmpl moDoc = null;

        /**
         * 执行科室
         */
        public DawnOrgDept execDept = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
