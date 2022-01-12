package com.kaos.his.entity.inpatient.surgery;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.common.Department;
import com.kaos.his.entity.common.Employee;
import com.kaos.his.entity.inpatient.Inpatient;
import com.kaos.his.enums.AnesTypeEnum;
import com.kaos.his.enums.MetOpsInciTypeEnum;
import com.kaos.his.enums.SurgeryDegreeEnum;
import com.kaos.his.enums.SurgeryInspectResultEnum;
import com.kaos.his.enums.SurgeryKindEnum;
import com.kaos.his.enums.SurgeryStatusEnum;
import com.kaos.his.enums.ValidStateEnum;

/**
 * 手术申请（XYHIS.MET_OPS_APPLY）
 */
public class MetOpsApply {
    /**
     * 手术编码
     */
    public String operationNo = null;

    /**
     * 门诊：门诊号；
     * 住院：住院流水号；
     */
    public String clinicCode = null;

    /**
     * 住院号
     */
    public String patientNo = null;

    /**
     * 手术诊断
     */
    public String diagnosis = null;

    /**
     * 手术类型
     */
    public SurgeryKindEnum surgeryKind = null;

    /**
     * 手术医生编码
     */
    public String opsDocCode = null;

    /**
     * 指导医生编码
     */
    public String guiDocCode = null;

    /**
     * 预约时间（计划手术时间）
     */
    public Date preDate = null;

    /**
     * 麻醉类型
     */
    public AnesTypeEnum anesType = null;

    /**
     * 手术科室（三个院区各自的手术室）
     */
    public String surgeryDeptCode = null;

    /**
     * 申请医生编码
     */
    public String applyDocCode = null;

    /**
     * 申请时间
     */
    public Date applyDate = null;

    /**
     * 申请备注
     */
    public String applyNote = null;

    /**
     * 审批医生编码
     */
    public String apprDocCode = null;

    /**
     * 审批时间
     */
    public Date apprDate = null;

    /**
     * 审批备注
     */
    public String apprNote = null;

    /**
     * 麻醉医生编码
     */
    public String anesDocCode = null;

    /**
     * 手术等级
     */
    public SurgeryDegreeEnum degree = null;

    /**
     * 切口类型
     */
    public MetOpsInciTypeEnum inciType = null;

    /**
     * 检验结果
     */
    public SurgeryInspectResultEnum inpectResult = null;

    /**
     * 手术状态
     */
    public SurgeryStatusEnum surgeryStatus = null;

    /**
     * 结束标识
     */
    public Boolean finishFlag = null;

    /**
     * 是否已计费
     */
    public Boolean chargeFlag = null;

    /**
     * 是否重症
     */
    public Boolean heavyFlag = null;

    /**
     * 是否特殊手术
     */
    public Boolean specialFlag = null;

    /**
     * 申请人编码
     */
    public String operCode = null;

    /**
     * 申请时间
     */
    public Date operDate = null;

    /**
     * 有效标识
     */
    public ValidStateEnum valid = null;

    /**
     * 手术间编码
     */
    public String roomId = null;

    /**
     * 手术部位|手术体位
     */
    public String position = null;

    /**
     * 作废标识
     */
    public Boolean cancelFlag = null;

    /**
     * 发布标识，发布后医生可见
     */
    public Boolean publishFlag = null;

    /**
     * 手术标识
     */
    public String operRemark = null;

    /**
     * 门诊流水号（日间手术专用）
     */
    public String daySurgeryClinicCode = null;

    /**
     * 术中冰冻标识
     */
    public Boolean frozenFlag = null;

    /**
     * 术中冰冻开始时间
     */
    public Date frozenBeginDate = null;

    /**
     * 术中冰冻结束时间
     */
    public Date frozenEndDate = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 手术项目
         */
        public MetOpsItem metOpsItem = null;

        /**
         * 手术安排数据
         */
        public List<MetOpsArrange> metOpsArranges = null;

        /**
         * 住院患者
         */
        public Inpatient inpatient = null;

        /**
         * 实体：手术医生
         */
        public Employee opsDoctor = null;

        /**
         * 实体：指导医生
         */
        public Employee guiDoctor = null;

        /**
         * 实体：手术科室
         */
        public Department surgeryDept = null;

        /**
         * 实体：申请医生
         */
        public Employee applyDoctor = null;

        /**
         * 实体：审批医生
         */
        public Employee apprDoctor = null;

        /**
         * 麻醉医生
         */
        public Employee anesDoctor = null;

        /**
         * 实体：申请人
         */
        public Employee operator = null;

        /**
         * 实体：手术间
         */
        public MetOpsRoom room = null;
    }

    /**
     * 与本实体相关联的其他实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
