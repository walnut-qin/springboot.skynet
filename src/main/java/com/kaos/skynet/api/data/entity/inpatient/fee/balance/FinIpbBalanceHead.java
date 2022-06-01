package com.kaos.skynet.api.data.entity.inpatient.fee.balance;

import java.util.Date;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.kaos.skynet.api.data.enums.TransTypeEnum;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体：结算头表（XYHIS.FIN_IPB_BALANCEHEAD）
 */
@Getter
@Setter
@Builder
public class FinIpbBalanceHead {
    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 交易类型
     */
    private TransTypeEnum transType;

    /**
     * 住院流水号
     */
    private String inpatientNo;

    /**
     * 结算序号
     */
    private Integer balanceNo;

    /**
     * 医保编码
     */
    private String pactCode;

    /**
     * 结算员编码
     */
    private String balanceEmplCode;

    /**
     * 结算时间
     */
    private Date balanceDate;

    /**
     * 总费用，实际交易的费用总额，和明细总和的差值即为四舍五入量
     */
    private Double totCost;

    /**
     * 医保：账户消费
     */
    private Double payCost;

    /**
     * 医保：统筹消费
     */
    private Double pubCost;

    /**
     * 自费：预交金
     */
    private Double prepayCost;

    /**
     * 自费：结算时补交金额
     */
    private Double supplyCost;

    /**
     * 自费：结算时返还金额
     */
    private Double returnCost;

    /**
     * 政策：优惠金额
     */
    private Double ecoCost;

    /**
     * 政策：减免金额
     */
    private Double derCost;

    /**
     * 政策：公务员补助
     */
    private Double officePay;

    /**
     * 政策：大额补助
     */
    private Double largePay;

    /**
     * 政策：老红军补贴
     */
    private Double militaryPay;

    /**
     * 政策：残联基金
     */
    private Double clCost;

    /**
     * 政策：精准扶贫
     */
    private Double jzfpCost;

    /**
     * 政策：公益宝
     */
    private Double gybCost;

    /**
     * 政策：医院承担
     */
    private Double hosCost;

    /**
     * 日结编号
     */
    private String dayReportNo;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIpbBalanceHead) {
            var that = (FinIpbBalanceHead) arg0;
            return StringUtils.equals(this.invoiceNo, that.invoiceNo)
                    && this.transType == that.transType;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(invoiceNo, transType);
    }

    /**
     * 根据当前结算数据计算四舍五入
     * 
     * @return
     */
    public Double getRound() {
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
                    String.format("结算数据分析中, 四舍五入值过大, 可能存在异常(invoiceNo = %s, round = %f)", this.invoiceNo, round));
        }

        return round;
    }
}
