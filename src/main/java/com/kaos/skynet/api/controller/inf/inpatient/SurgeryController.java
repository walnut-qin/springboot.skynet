package com.kaos.skynet.api.controller.inf.inpatient;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.AnesTypeEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryDegreeEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryInspectResultEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryStatusEnum;

public interface SurgeryController {
    /**
     * 查询手术记录
     * 
     * @param req
     * @return
     */
    QueryAppliesRsp querySurgeries(@Valid QueryAppliesReq req);

    /**
     * 请求体
     */
    public static class QueryAppliesReq {
        /**
         * 科室编码
         */
        public String deptCode = null;

        /**
         * 手术室编码
         */
        public String roomNo = null;

        /**
         * 开始时间
         */
        @NotNull(message = "开始时间不能为空")
        public LocalDateTime beginDate = null;

        /**
         * 结束时间
         */
        @NotNull(message = "结束时间不能为空")
        public LocalDateTime endDate = null;

        /**
         * 状态清单
         */
        public List<SurgeryStatusEnum> states = null;
    }

    /**
     * 响应体
     */
    public class QueryAppliesRsp {
        /**
         * 结果集数量
         */
        public Integer size = null;

        /**
         * 数据
         */
        public List<Data> data = null;

        /**
         * 数据集
         */
        public static class Data {
            /**
             * 手术间
             */
            public String roomNo = null;

            /**
             * 预约时间
             */
            public LocalDateTime apprDate = null;

            /**
             * 住院号
             */
            public String patientNo = null;

            /**
             * 科室
             */
            public String deptName = null;

            /**
             * 床号
             */
            public String bedNo = null;

            /**
             * 姓名
             */
            public String name = null;

            /**
             * 性别
             */
            public SexEnum sex = null;

            /**
             * 年龄
             */
            public Period age = null;

            /**
             * 诊断
             */
            public String diagnosis = null;

            /**
             * 手术名称
             */
            public String surgeryName = null;

            /**
             * 手术标识
             */
            public String operRemark = null;

            /**
             * 手术分级
             */
            public SurgeryDegreeEnum degree = null;

            /**
             * 术者
             */
            public String surgeryDocName = null;

            /**
             * 助手
             */
            public String helperNames = null;

            /**
             * 麻醉种类
             */
            public AnesTypeEnum anesType = null;

            /**
             * 主麻
             */
            public String anesDoc1 = null;

            /**
             * 副麻
             */
            public String anesDoc2 = null;

            /**
             * 洗手护士
             */
            public String washNurseNames = null;

            /**
             * 巡回护士
             */
            public String itinerantNurseNames = null;

            /**
             * 特殊要求
             */
            public String applyNote = null;

            /**
             * 检验结果
             */
            public SurgeryInspectResultEnum inspectResult = null;

            /**
             * 是否公布
             */
            public String publishFlag = null;

            /**
             * ERAS信息
             */
            public String eras = null;

            /**
             * VTE信息
             */
            public String vte = null;

            /**
             * 手术状态 (手麻系统中的状态)
             */
            public String surgeryState = null;
        }
    }
}
