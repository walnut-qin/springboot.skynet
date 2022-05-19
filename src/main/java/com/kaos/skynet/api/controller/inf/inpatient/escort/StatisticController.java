package com.kaos.skynet.api.controller.inf.inpatient.escort;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.enums.common.HealthCodeEnum;
import com.kaos.skynet.api.enums.common.TravelCodeEnum;

public interface StatisticController {
    /**
     * 查询指定科室的患者信息，包括陪护
     * 
     * @param deptCode 科室编码
     * @return
     */
    List<QueryEscortRsp> queryEscortData(@NotNull(message = "科室编码不能为空") String deptCode);

    public static class QueryEscortRsp {
        /**
         * 入院时间
         */
        public Date inDate = null;

        /**
         * 床号
         */
        public String bedNo = null;

        /**
         * 姓名
         */
        public String name = null;

        /**
         * 就诊卡号
         */
        public String cardNo = null;

        /**
         * 核酸结果
         */
        public String nucleicAcidResult = null;

        /**
         * 健康码
         */
        public HealthCodeEnum healthCode = null;

        /**
         * 行程码
         */
        public TravelCodeEnum travelCode = null;

        /**
         * 14天内是否去过高风险地区
         */
        public String highRiskFlag = null;

        /**
         * 到过高风险地区清单
         */
        public String highRiskArea = null;

        /**
         * 陪护1姓名
         */
        public String escort1Name = null;

        /**
         * 陪护1就诊卡号
         */
        public String escort1CardNo = null;

        /**
         * 陪护1就诊卡号
         */
        public String escort1IdenNo = null;

        /**
         * 陪护1核酸结果
         */
        public String escort1NucleicAcidResult = null;

        /**
         * 陪护1联系电话
         */
        public String escort1Tel = null;

        /**
         * 陪护1健康码
         */
        public HealthCodeEnum escort1HealthCode = null;

        /**
         * 陪护1行程码
         */
        public TravelCodeEnum escort1TravelCode = null;

        /**
         * 陪护1高风险标识
         */
        public String escort1HighRiskFlag = null;

        /**
         * 陪护1高风险地区
         */
        public String escort1HighRiskArea = null;

        /**
         * 陪护1姓名
         */
        public String escort2Name = null;

        /**
         * 陪护1就诊卡号
         */
        public String escort2CardNo = null;

        /**
         * 陪护1就诊卡号
         */
        public String escort2IdenNo = null;

        /**
         * 陪护1核酸结果
         */
        public String escort2NucleicAcidResult = null;

        /**
         * 陪护1联系电话
         */
        public String escort2Tel = null;

        /**
         * 陪护2健康码
         */
        public HealthCodeEnum escort2HealthCode = null;

        /**
         * 陪护2行程码
         */
        public TravelCodeEnum escort2TravelCode = null;

        /**
         * 陪护2高风险标识
         */
        public String escort2HighRiskFlag = null;

        /**
         * 陪护2高风险地区
         */
        public String escort2HighRiskArea = null;
    }
}
