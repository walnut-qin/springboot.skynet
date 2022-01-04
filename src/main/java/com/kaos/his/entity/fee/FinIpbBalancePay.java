package com.kaos.his.entity.fee;

import com.kaos.his.enums.BalancePayTransKindEnum;
import com.kaos.his.enums.PayWayEnum;
import com.kaos.his.enums.TransTypeEnum;

/**
 * 实付表
 */
public class FinIpbBalancePay {
    /**
     * 发票号
     */
    public String invoiceNo = null;

    /**
     * 交易类型
     */
    public TransTypeEnum transType = null;

    /**
     * 交易种类
     */
    public BalancePayTransKindEnum transKind = null;

    /**
     * 付款方式
     */
    public PayWayEnum payWay = null;

    /**
     * 交易金额
     */
    public Double cost = null;
}
