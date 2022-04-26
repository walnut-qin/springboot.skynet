package com.kaos.skynet.api.controller.entity.inpatient.surgery;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.enums.impl.common.SexEnum;
import com.kaos.skynet.enums.impl.common.ValidStateEnum;
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
             * 手术间
             */
            @Getter
            @Setter
            private String roomNo = null;

            /**
             * 手术时间
             */
            @Setter
            private LocalDateTime apprDate = null;

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
            private String operationName = null;
        }
    }
}
