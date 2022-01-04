package com.kaos.his.entity.fee;

import java.util.List;

import com.google.gson.annotations.Expose;

public class DayBalance {
    /**
     * 日结明细
     */
    public static class Detail {
        /**
         * 住院号
         */
        public String inpatientNo = null;

        /**
         * 姓名
         */
        public String name = null;

        /**
         * 明细数值
         */
        public Double value = null;
    }

    /**
     * 费用合计
     */
    public Double totCost = null;

    /**
     * 费用总计 - 明细
     */
    @Expose(serialize = false, deserialize = false)
    public List<Detail> totCosts = null;
}
