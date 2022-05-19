package com.kaos.skynet.api.entity.inpatient.surgery;

import java.time.LocalDateTime;
import java.util.Map;

import com.kaos.skynet.api.entity.common.DawnOrgDept;
import com.kaos.skynet.api.entity.common.DawnOrgEmpl;
import com.kaos.skynet.api.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.enums.common.ValidStateEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.AnesTypeEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.MetOpsInciTypeEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryArrangeRoleEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryDegreeEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryInspectResultEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryKindEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryStatusEnum;

import lombok.Getter;

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
     * 住院号 {@link MetOpsApply.AssociateEntity#inMainInfo}
     */
    public String patientNo = null;

    /**
     * 住院科室, 由于患者可能存在转科, 由该字段记录申请手术的时候所在的科室
     */
    @Getter
    private String inDeptCode = null;

    /**
     * 手术诊断
     */
    public String diagnosis = null;

    /**
     * 手术类型
     */
    public SurgeryKindEnum surgeryKind = null;

    /**
     * 手术医生编码 {@link MetOpsApply.AssociateEntity#opsDoc}
     */
    public String opsDocCode = null;

    /**
     * 指导医生编码 {@link MetOpsApply.AssociateEntity#guiDoc}
     */
    public String guiDocCode = null;

    /**
     * 预约时间（计划手术时间）
     */
    public LocalDateTime preDate = null;

    /**
     * 麻醉类型
     */
    public AnesTypeEnum anesType = null;

    /**
     * 手术科室（三个院区各自的手术室） {@link MetOpsApply.AssociateEntity#surgeryDept}
     */
    public String surgeryDeptCode = null;

    /**
     * 申请医生编码 {@link MetOpsApply.AssociateEntity#applyDoc}
     */
    public String applyDocCode = null;

    /**
     * 申请时间
     */
    public LocalDateTime applyDate = null;

    /**
     * 申请备注
     */
    public String applyNote = null;

    /**
     * 审批医生编码 {@link MetOpsApply.AssociateEntity#apprDoc}
     */
    public String apprDocCode = null;

    /**
     * 审批时间
     */
    public LocalDateTime apprDate = null;

    /**
     * 审批备注
     */
    public String apprNote = null;

    /**
     * 麻醉医生编码 {@link MetOpsApply.AssociateEntity#anesDoc}
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
     * 是否首台
     */
    @Getter
    private Boolean firstFlag = null;

    /**
     * 检验结果 [BLOOD_NUM]
     */
    public SurgeryInspectResultEnum inspectResult = null;

    /**
     * 台次
     */
    @Getter
    private String order = null;

    /**
     * 手术状态
     */
    public SurgeryStatusEnum surgeryStatus = null;

    /**
     * 结束标识
     */
    public Boolean finishFlag = null;

    /**
     * 麻醉标识
     */
    public Boolean anesFlag = null;

    /**
     * 非计划标识
     */
    public Boolean unplannedFlag = null;

    /**
     * 非计划手术家属签字
     */
    public Boolean signedFlag = null;

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
     * 申请人编码 {@link MetOpsApply.AssociateEntity#operEmpl}
     */
    public String operCode = null;

    /**
     * 申请时间
     */
    public LocalDateTime operDate = null;

    /**
     * 有效标识
     */
    public ValidStateEnum valid = null;

    /**
     * 手术间编码 {@link MetOpsApply.AssociateEntity#room}
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
     * 手术名称备注
     */
    public String surgeryNameNote = null;

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
    public LocalDateTime frozenBeginDate = null;

    /**
     * 术中冰冻结束时间
     */
    public LocalDateTime frozenEndDate = null;

    /**
     * 术后是否转入ICU
     */
    @Getter
    private Boolean icuFlag = null;

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
        public Map<SurgeryArrangeRoleEnum, MetOpsArrange> metOpsArranges = null;

        /**
         * 住院患者 {@link MetOpsApply#patientNo}
         */
        public FinIprInMainInfo inMainInfo = null;

        /**
         * 实体：手术医生 {@link MetOpsApply#opsDocCode}
         */
        public DawnOrgEmpl opsDoc = null;

        /**
         * 实体：指导医生 {@link MetOpsApply#guiDocCode}
         */
        public DawnOrgEmpl guiDoc = null;

        /**
         * 实体：手术科室 {@link MetOpsApply#surgeryDeptCode}
         */
        public DawnOrgDept surgeryDept = null;

        /**
         * 实体：申请医生 {@link MetOpsApply#applyDocCode}
         */
        public DawnOrgEmpl applyDoc = null;

        /**
         * 实体：审批医生 {@link MetOpsApply#apprDocCode}
         */
        public DawnOrgEmpl apprDoc = null;

        /**
         * 麻醉医生 {@link MetOpsApply#anesDocCode}
         */
        public DawnOrgEmpl anesDoc = null;

        /**
         * 实体：申请人 {@link MetOpsApply#operCode}
         */
        public DawnOrgEmpl operEmpl = null;

        /**
         * 实体：手术间 {@link MetOpsApply#roomId}
         */
        public MetOpsRoom room = null;
    }

    /**
     * 与本实体相关联的其他实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
