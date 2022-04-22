package com.kaos.skynet.api.controller.entity.inpatient.surgery;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.enums.impl.common.ValidStateEnum;
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
        private LocalDateTime beginPreDate = null;

        /**
         * 申请时间右值
         */
        @Getter
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
             * 手术名
             */
            @Setter
            private String operationName = null;
        }
    }
}
