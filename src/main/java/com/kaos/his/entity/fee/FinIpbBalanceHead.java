package com.kaos.his.entity.fee;

import com.kaos.his.enums.TransTypeEnum;

/**
 * 结算头表实体
 */
public class FinIpbBalanceHead {
    /**
     * 发票号
     */
    public String invoiceNo = null;

    /**
     * 交易类型
     */
    public TransTypeEnum transType = null;

    /**
     * 住院流水号
     */
    public String inpatientNo = null;

    /**
     * 结算序号
     */
    public Integer balanceNo = null;

    /**
     * 总费用，实际交易的费用总额，和明细总和的差值即为四舍五入量
     */
    public Double totCost = null;
}
