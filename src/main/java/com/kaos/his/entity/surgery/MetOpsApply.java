package com.kaos.his.entity.surgery;

import java.util.Date;

import com.kaos.his.entity.personnel.Inpatient;
import com.kaos.his.enums.AnesTypeEnum;
import com.kaos.his.enums.SurgeryDegreeEnum;
import com.kaos.his.enums.SurgeryKindEnum;
import com.kaos.his.enums.SurgeryStatusEnum;

public class MetOpsApply {
    /**
     * 手术编码
     */
    public String operationNo = null;

    /**
     * 手术名称（非表字段，联合查询得出）
     */
    public String operationName = null;

    /**
     * 手术间
     */
    public String roomId = null;

    /**
     * 住院号
     */
    public String patientNo = null;

    /**
     * 手术类型
     */
    public SurgeryKindEnum surgeryKind = null;

    /**
     * 手术医生编码
     */
    public String opsDocCode = null;

    /**
     * 预约日期
     */
    public Date preDate = null;

    /**
     * 麻醉方法
     */
    public AnesTypeEnum anesType = null;

    /**
     * 手术科室（根据该字段判断院区）
     */
    public String execDeptCode = null;

    /**
     * 申请医生编码
     */
    public String applyDocCode = null;

    /**
     * 申请时间
     */
    public String applyDate = null;

    /**
     * 审批医生编码
     */
    public String apprDocCode = null;

    /**
     * 审批日期
     */
    public String apprDate = null;

    /**
     * 麻醉医生编码
     */
    public String anesDocCode = null;

    /**
     * 手术分级
     */
    public SurgeryDegreeEnum surgeryDegree = null;

    /**
     * 手术状态
     */
    public SurgeryStatusEnum surgeryStatus = null;

    /**
     * 诊断
     */
    public String diagnosis = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 手术间
         */
        public Room room = null;

        /**
         * 住院患者实体
         */
        public Inpatient inpatient = null;
    }

    /**
     * 与本实体相关联的其他实体
     */
    public AssociateEntity associateEntity = null;
}
