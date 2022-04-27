package com.kaos.skynet.api.controller.entity.inpatient.surgery;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.enums.impl.common.SexEnum;
import com.kaos.skynet.enums.impl.common.ValidStateEnum;
import com.kaos.skynet.enums.impl.inpatient.surgery.AnesTypeEnum;
import com.kaos.skynet.enums.impl.inpatient.surgery.MetOpsInciTypeEnum;
import com.kaos.skynet.enums.impl.inpatient.surgery.SurgeryDegreeEnum;
import com.kaos.skynet.enums.impl.inpatient.surgery.SurgeryInspectResultEnum;
import com.kaos.skynet.enums.impl.inpatient.surgery.SurgeryKindEnum;
import com.kaos.skynet.enums.impl.inpatient.surgery.SurgeryStatusEnum;

import lombok.Getter;
import lombok.Setter;

public class QuerySurgeryApplies {
    /**
     * 请求消息体
     */
    public static class Request {
        /**
         * 登入科室编码
         */
        @Getter
        private String loginDeptCode = null;

        /**
         * 申请时间左值
         */
        @Getter
        @NotNull(message = "申请时间左值不能为空")
        private LocalDateTime beginPreDate = null;

        /**
         * 申请时间右值
         */
        @Getter
        @NotNull(message = "申请时间右值不能为空")
        private LocalDateTime endPreDate = null;

        /**
         * 执行状态列表
         */
        @Getter
        private List<SurgeryStatusEnum> execStatus = null;

        /**
         * 是否已麻醉
         */
        @Getter
        private Boolean anesFlag = null;

        /**
         * 有效标识
         */
        @Getter
        private ValidStateEnum valid = null;
    }

    /**
     * 响应消息体
     */
    public static class Response {
        /**
         * 结果集数量
         */
        @Setter
        private Integer size = null;

        /**
         * 数据集
         */
        @Setter
        private List<DataItem> data = null;

        /**
         * 数据项
         */
        public static class DataItem {
            /**
             * 序号
             */
            @Setter
            private Integer No = null;

            /**
             * 手术间
             */
            @Getter
            @Setter
            private String roomNo = null;

            /**
             * 预约时间
             */
            @Getter
            @Setter
            private LocalTime preDate = null;

            /**
             * 手术类型
             */
            @Setter
            private SurgeryKindEnum surgeryKind = null;

            /**
             * 是否为首台
             */
            @Setter
            private Boolean firstFlag = null;

            /**
             * 患者住院科室
             */
            @Setter
            private String deptName = null;

            /**
             * 住院号
             */
            @Setter
            private String patientNo = null;

            /**
             * 患者姓名
             */
            @Setter
            private String name = null;

            /**
             * 性别
             */
            @Setter
            private SexEnum sex = null;

            /**
             * 年龄
             */
            @Setter
            private String age = null;

            /**
             * 台次
             */
            @Setter
            private String order = null;

            /**
             * 术前诊断
             */
            @Setter
            private String preDiagnosis = null;

            /**
             * 手术名
             */
            @Setter
            private String surgeryName = null;

            /**
             * 非计划手术标识
             */
            @Setter
            private Boolean unplannedFlag = null;

            /**
             * 已申请标识
             */
            @Setter
            private Boolean signedFlag = null;

            /**
             * 麻醉方式
             */
            @Setter
            private AnesTypeEnum anesType = null;

            /**
             * 手术医生姓名
             */
            @Setter
            private String surgeryDoctor = null;

            /**
             * 主麻
             */
            @Setter
            private String masterAnesDoctor = null;

            /**
             * 副麻
             */
            @Setter
            private String slaveAnesDoctor = null;

            /**
             * 主洗手护士
             */
            @Setter
            private String masterWashNurse = null;

            /**
             * 副洗手护士
             */
            @Setter
            private String slaveWashNurse = null;

            /**
             * 主巡回护士
             */
            @Setter
            private String masterItinerantNurse = null;

            /**
             * 副巡回护士
             */
            @Setter
            private String slaveItinerantNurse = null;

            /**
             * 特殊需求
             */
            @Setter
            private String specialNote = null;

            /**
             * 检验结果
             */
            @Setter
            private SurgeryInspectResultEnum inspectResult = null;

            /**
             * 手术等级
             */
            @Setter
            private SurgeryDegreeEnum surgeryDegree = null;

            /**
             * 切口类型
             */
            @Setter
            private MetOpsInciTypeEnum incisionType = null;

            /**
             * 一助
             */
            @Setter
            private String helper1 = null;

            /**
             * 二助
             */
            @Setter
            private String helper2 = null;

            /**
             * 三助
             */
            @Setter
            private String helper3 = null;

            /**
             * 审批人
             */
            @Setter
            private String apprEmpl = null;

            /**
             * 审批时间
             */
            @Setter
            private LocalDateTime apprDate = null;

            /**
             * 审批意见
             */
            @Setter
            private String apprNote = null;

            /**
             * 手术二
             */
            @Setter
            private String surgeryNameNote = null;

            /**
             * ERAS标识
             */
            @Setter
            private Boolean eras = null;

            /**
             * VTE等级
             */
            @Setter
            private String vte = null;
        }
    }
}
