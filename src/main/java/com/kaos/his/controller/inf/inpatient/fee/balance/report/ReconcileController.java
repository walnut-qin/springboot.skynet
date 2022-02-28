package com.kaos.his.controller.inf.inpatient.fee.balance.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.kaos.his.entity.inpatient.fee.FinIpbFeeInfo;
import com.kaos.his.entity.inpatient.fee.FinIpbItemList;
import com.kaos.his.entity.inpatient.fee.FinIpbMedicineList;

import org.springframework.validation.annotation.Validated;

@Validated
public interface ReconcileController {
    /**
     * 费用和药品/非药品对账
     * 
     * @param req
     * @return
     */
    CheckRsp check(@NotNull(message = "body不能为空") CheckReq req);

    @Validated
    public static class CheckReq {
        /**
         * 开始时间
         */
        @NotNull
        public Date beginDate = null;

        /**
         * 结束时间
         */
        @NotNull
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
             * HIS收入
             */
            public Double his = null;

            /**
             * 拥有收入
             */
            public Double yongYou = null;

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
}
