package com.kaos.his.entity.fee;

import com.google.common.base.Optional;
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

    /**
     * 医保：账户消费
     */
    public Double payCost = null;

    /**
     * 医保：统筹消费
     */
    public Double pubCost = null;

    /**
     * 自费：预交金
     */
    public Double prepayCost = null;

    /**
     * 自费：结算时补交金额
     */
    public Double supplyCost = null;

    /**
     * 自费：结算时返还金额
     */
    public Double returnCost = null;

    /**
     * 政策：优惠金额
     */
    public Double ecoCost = null;

    /**
     * 政策：减免金额
     */
    public Double derCost = null;

    /**
     * 政策：公务员补助
     */
    public Double officePay = null;

    /**
     * 政策：大额补助
     */
    public Double largePay = null;

    /**
     * 政策：老红军补贴
     */
    public Double militaryPay = null;

    /**
     * 政策：残联基金
     */
    public Double clCost = null;

    /**
     * 政策：精准扶贫
     */
    public Double jzfpCost = null;

    /**
     * 政策：公益宝
     */
    public Double gybCost = null;

    /**
     * 政策：医院承担
     */
    public Double hosCost = null;

    /**
     * 计算四舍五入值(总费用 = 自费 + 医保 + 政策 + 四舍五入)
     * 
     * @return
     */
    public Double GetRound() {
        // 计算原始数据
        var tot = Optional.fromNullable(this.totCost).or(0.00);
        var own = Optional.fromNullable(this.prepayCost).or(0.00) + Optional.fromNullable(this.supplyCost).or(0.00)
                - Optional.fromNullable(this.returnCost).or(0.00);
        var med = Optional.fromNullable(this.pubCost).or(0.00) + Optional.fromNullable(this.payCost).or(0.00);
        var der = Optional.fromNullable(this.ecoCost).or(0.00) + Optional.fromNullable(this.derCost).or(0.00)
                + Optional.fromNullable(this.officePay).or(0.00) + Optional.fromNullable(this.largePay).or(0.00)
                + Optional.fromNullable(this.militaryPay).or(0.00) + Optional.fromNullable(this.clCost).or(0.00)
                + Optional.fromNullable(this.jzfpCost).or(0.00) + Optional.fromNullable(this.gybCost).or(0.00)
                + Optional.fromNullable(this.hosCost).or(0.00);

        // 计算四舍五入
        var round = tot - (own + med + der);
        if (Math.abs(round) > 0.1) {
            throw new RuntimeException(
                    String.format("结算数据分析中，四舍五入值过大，可能存在异常(invoiceNo = %s, round = %f)", this.invoiceNo, round));
        }

        return round;
    }
}
