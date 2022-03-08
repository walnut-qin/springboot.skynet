package com.kaos.skynet.api.controller.inf.inpatient.fee;

import java.util.List;

public interface PrepayController {
    /**
     * 修改预交金
     * 
     * @param patientNo 住院号
     * @return
     */
    FixPrepayCostRsp fixPrepayCost(String patientNo);

    public static class FixPrepayCostRsp {
        /**
         * 住院号
         */
        public String patientNo = null;

        /**
         * 修改数量
         */
        public Integer size = null;

        /**
         * 修改明细
         */
        public List<ModifiedData> modifiedDatas = null;

        public static class ModifiedData {
            /**
             * 序号
             */
            public Integer happenNo = null;

            /**
             * 原值
             */
            public Double orgPrepayCost = null;

            /**
             * 新值
             */
            public Double dstPrepayCost = null;
        }
    }
}
