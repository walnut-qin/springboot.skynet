package com.kaos.skynet.api.controller.inf.inpatient.fee.balance.report;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.entity.inpatient.fee.FinIpbFeeInfo;
import com.kaos.skynet.api.entity.inpatient.fee.FinIpbItemList;
import com.kaos.skynet.api.entity.inpatient.fee.FinIpbMedicineList;
import com.kaos.skynet.api.entity.inpatient.fee.balance.FinIpbBalanceHead;
import com.kaos.skynet.api.enums.common.DeptOwnEnum;

public interface ReconcileController {
    /**
     * 费用和药品/非药品对账
     * 
     * @param req
     * @return
     */
    CheckRsp check(CheckReq req);

    public static class CheckReq {
        /**
         * 开始时间
         */
        @NotNull(message = "开始时间不能为空")
        public Date beginDate = null;

        /**
         * 结束时间
         */
        @NotNull(message = "结束时间不能为空")
        public Date endDate = null;
    }

    public static class CheckRsp {
        /**
         * 数量
         */
        public Integer size = null;

        /**
         * 各科室数据 dept -> Data
         */
        public Map<String, Data> data = null;

        /**
         * 明细数据
         */
        public static class Data {
            /**
             * 数量
             */
            public Integer size = null;

            /**
             * 明细数据 recipeNo -> SubData
             */
            public Map<String, SubData> data = null;

            /**
             * 子明细数据
             */
            public static class SubData {
                /**
                 * 费用信息
                 */
                public List<FinIpbFeeInfo> feeInfo = null;

                /**
                 * 非药品信息
                 */
                public List<FinIpbItemList> itemList = null;

                /**
                 * 药品信息
                 */
                public List<FinIpbMedicineList> medList = null;
            }
        }
    }

    /**
     * 导出明细
     * 
     * @param req
     * @return
     */
    ExportNewYbDataRsp exportNewYbData(ExportNewYbDataReq req);

    public static class ExportNewYbDataReq {
        /**
         * 开始时间
         */
        @NotNull(message = "开始时间不能为空")
        public Date beginDate = null;

        /**
         * 结束时间
         */
        @NotNull(message = "结束时间不能为空")
        public Date endDate = null;

        /**
         * 院区不能为空
         */
        @NotNull(message = "院区不能为空")
        public DeptOwnEnum deptOwn = null;
    }

    public static class ExportNewYbDataRsp {
        /**
         * 尺寸
         */
        public Integer size = null;

        /**
         * 数据
         */
        public Map<String, Data> data = null;

        /**
         * 数据
         */
        public static class Data {
            /**
             * 花费明细
             */
            public CostData costData = null;

            /**
             * 明细
             */
            public static class CostData {
                /**
                 * 统筹
                 */
                public Double pubCost = null;

                /**
                 * 账户
                 */
                public Double payCost = null;
            }

            public Map<String, Collection<FinIpbBalanceHead>> detail = null;
        }
    }
}
