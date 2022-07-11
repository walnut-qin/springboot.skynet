package com.kaos.skynet.api.data.docare.entity.medsurgery;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict.SurgeryLevelEnum;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.IntegerUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MedOperationMaster {
    /**
     * 住院号
     */
    private String patientId;

    /**
     * 住院标识，门诊为0
     */
    private Integer visitId;

    /**
     * 手术号
     */
    private Integer operId;

    /**
     * 病人所在科室，即申请科室
     */
    private String deptStayed;

    /**
     * 手术室，即手术科室代码
     */
    private String operatingRoom;

    /**
     * 手术间ID，详见手术间字典
     */
    private String operatingRoomNo;

    /**
     * 术前诊断
     */
    private String diagBeforeOperation;

    /**
     * 病情说明
     */
    private String patientCondition;

    /**
     * 手术等级
     */
    private SurgeryLevelEnum operationScale;

    /**
     * 术后诊断
     */
    private String diagAfterOperation;

    /**
     * 急诊标识<急诊/择期>
     */
    private Boolean emergencyIndicator;

    /**
     * 执行手术的科室
     */
    private String operatingDept;

    /**
     * 主刀医师
     */
    private String surgeon;

    /**
     * 一助
     */
    private String helper1;

    /**
     * 二助
     */
    private String helper2;

    /**
     * 三助
     */
    private String helper3;

    /**
     * 四助
     */
    private String helper4;

    /**
     * 麻醉方式名称
     */
    private String anesName;

    /**
     * 主麻
     */
    private String anesDoctor;

    /**
     * 麻醉助手
     */
    private String anesAssistant;

    /**
     * 洗手护士1
     */
    private String washNurse1;

    /**
     * 洗手护士2
     */
    private String washNurse2;

    /**
     * 巡回护士1
     */
    private String itinerantNurse1;

    /**
     * 巡回护士2
     */
    private String itinerantNurse2;

    /**
     * 手术开始时间
     */
    private LocalDateTime startDateTime;

    /**
     * 手术结束时间
     */
    private LocalDateTime endDateTime;

    /**
     * 手术状态
     */
    private OperStatusEnum operStatus;

    /**
     * 入手术室时间
     */
    private LocalDateTime inDateTime;

    /**
     * 出手术室时间
     */
    private LocalDateTime outDateTime;

    /**
     * 手术排程时间
     */
    private LocalDateTime scheduledDateTime;

    /**
     * 切口类型
     */
    private String inciType;

    /**
     * 手术名称
     */
    private String operationName;

    /**
     * 麻醉开始时间
     */
    private LocalDateTime anesStartTime;

    /**
     * 麻醉结束时间
     */
    private LocalDateTime anesEndTime;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MedOperationMaster) {
            var that = (MedOperationMaster) arg0;
            return StringUtils.equals(this.patientId, that.patientId)
                    && IntegerUtils.equals(this.visitId, that.visitId)
                    && IntegerUtils.equals(this.operId, that.operId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(patientId, visitId, operId);
    }

    @Getter
    @AllArgsConstructor
    public enum OperStatusEnum implements Enum {
        手术取消("-80", "手术取消"),
        准备手术("0", "准备手术"),
        入手术室("5", "入手术室"),
        麻醉开始("10", "麻醉开始"),
        手术开始("15", "手术开始"),
        手术结束("25", "手术结束"),
        麻醉结束("30", "麻醉结束"),
        出手术室("35", "出手术室"),
        准备复苏("40", "准备复苏"),
        入复苏室("45", "入复苏室"),
        出复苏室("55", "出复苏室"),
        转入病房("60", "转入病房");

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
