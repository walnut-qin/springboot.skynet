package com.kaos.skynet.api.data.his.entity.inpatient.surgery;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.docare.entity.medcomm.MedAnesthesiaDict.AnesTypeEnum;
import com.kaos.skynet.api.data.his.enums.ValidEnum;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 手术申请（XYHIS.MET_OPS_APPLY）
 */
@Getter
@Setter
@Builder
public class MetOpsApply {
    /**
     * 手术编码
     */
    private String operationNo;

    /**
     * 门诊：门诊号；
     * 住院：住院流水号；
     */
    private String clinicCode;

    /**
     * 住院号 {@link MetOpsApply.AssociateEntity#inMainInfo}
     */
    private String patientNo;

    /**
     * 住院科室, 由于患者可能存在转科, 由该字段记录申请手术的时候所在的科室
     */
    @Getter
    private String inDeptCode;

    /**
     * 手术诊断
     */
    private String diagnosis;

    /**
     * 手术类型
     */
    private KindEnum surgeryKind;

    /**
     * 手术医生编码 {@link MetOpsApply.AssociateEntity#opsDoc}
     */
    private String opsDocCode;

    /**
     * 指导医生编码 {@link MetOpsApply.AssociateEntity#guiDoc}
     */
    private String guiDocCode;

    /**
     * 预约时间（计划手术时间）
     */
    private LocalDateTime preDate;

    /**
     * 麻醉类型
     */
    private AnesTypeEnum anesType;

    /**
     * 手术科室（三个院区各自的手术室） {@link MetOpsApply.AssociateEntity#surgeryDept}
     */
    private String surgeryDeptCode;

    /**
     * 申请医生编码 {@link MetOpsApply.AssociateEntity#applyDoc}
     */
    private String applyDocCode;

    /**
     * 申请时间
     */
    private LocalDateTime applyDate;

    /**
     * 申请备注
     */
    private String applyNote;

    /**
     * 审批医生编码 {@link MetOpsApply.AssociateEntity#apprDoc}
     */
    private String apprDocCode;

    /**
     * 审批时间
     */
    private LocalDateTime apprDate;

    /**
     * 审批备注
     */
    private String apprNote;

    /**
     * 麻醉医生编码 {@link MetOpsApply.AssociateEntity#anesDoc}
     */
    private String anesDocCode;

    /**
     * 手术等级
     */
    private DegreeEnum degree;

    /**
     * 切口类型
     */
    private InciTypeEnum inciType;

    /**
     * 是否首台
     */
    @Getter
    private Boolean firstFlag;

    /**
     * 检验结果 [BLOOD_NUM]
     */
    private InspectResultEnum inspectResult;

    /**
     * 台次
     */
    @Getter
    private String order;

    /**
     * 手术状态
     */
    private StatusEnum surgeryStatus;

    /**
     * 结束标识
     */
    private Boolean finishFlag;

    /**
     * 麻醉标识
     */
    private Boolean anesFlag;

    /**
     * 非计划标识
     */
    private Boolean unplannedFlag;

    /**
     * 非计划手术家属签字
     */
    private Boolean signedFlag;

    /**
     * 是否已计费
     */
    private Boolean chargeFlag;

    /**
     * 是否重症
     */
    private Boolean heavyFlag;

    /**
     * 是否特殊手术
     */
    private Boolean specialFlag;

    /**
     * 申请人编码 {@link MetOpsApply.AssociateEntity#operEmpl}
     */
    private String operCode;

    /**
     * 申请时间
     */
    private LocalDateTime operDate;

    /**
     * 有效标识
     */
    private ValidEnum valid;

    /**
     * 手术间编码 {@link MetOpsApply.AssociateEntity#room}
     */
    private String roomId;

    /**
     * 手术部位|手术体位
     */
    private String position;

    /**
     * 作废标识
     */
    private Boolean cancelFlag;

    /**
     * 发布标识，发布后医生可见
     */
    private Boolean publishFlag;

    /**
     * 手术名称备注
     */
    private String surgeryNameNote;

    /**
     * 手术标识
     */
    private String operRemark;

    /**
     * 门诊流水号（日间手术专用）
     */
    private String daySurgeryClinicCode;

    /**
     * 术中冰冻标识
     */
    private Boolean frozenFlag;

    /**
     * 术中冰冻开始时间
     */
    private LocalDateTime beginFrozenDate;

    /**
     * 术中冰冻结束时间
     */
    private LocalDateTime endFrozenDate;

    /**
     * 术后是否转入ICU
     */
    private Boolean icuFlag;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MetOpsApply) {
            var that = (MetOpsApply) arg0;
            return StringUtils.equals(this.operationNo, that.operationNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.operationNo);
    }

    /**
     * 手术切口类型
     */
    @Getter
    @AllArgsConstructor
    public enum InciTypeEnum implements Enum {
        I类切口("1", "I类切口"), II类切口("2", "II类切口"), III类切口("3", "III类切口"), IV类切口("4", "IV类切口"), 零类切口("5", "零类切口");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 手术等级
     */
    @Getter
    @AllArgsConstructor
    public enum DegreeEnum implements Enum {
        一级("1级", "一级"), 二级("2级", "二级"), 三级("3级", "三级"), 四级("4级", "四级");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 手术检验结果
     */
    @Getter
    @AllArgsConstructor
    public enum InspectResultEnum implements Enum {
        否("1", "否"), HAV("2", "HAV"), HBV("3", "HBV"), HCV("4", "HCV"), HIV("5", "HIV");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 手术类型
     */
    @Getter
    @AllArgsConstructor
    public enum KindEnum implements Enum {
        普通("1", "普通"), 急诊("2", "急诊"), 日间("3", "日间"), 痔瘘("4", "痔瘘"), 预约出院("5", "腔镜"), 择期("6", "择期");
    
        /**
         * 数据库存值
         */
        private String value;
    
        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 手术状态
     */
    @Getter
    @AllArgsConstructor
    public enum StatusEnum implements Enum {
        手术申请("1", "手术申请"), 手术审批("2", "手术审批"), 手术安排("3", "手术安排"), 手术完成("4", "手术完成"), 取消手术登记("5", "取消手术登记"), 手术审批未通过("6", "手术审批未通过");
    
        /**
         * 数据库存值
         */
        private String value;
    
        /**
         * 描述存值
         */
        private String description;
    }
}
