package com.kaos.skynet.api.data.his.entity.inpatient.fee;

import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.his.entity.inpatient.fee.balance.FinIpbBalanceHead.BalanceStateEnum;
import com.kaos.skynet.api.data.his.enums.DeptOwnEnum;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.IntegerUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FinIpbInPrepay {
    /**
     * 住院流水号 {@link FinIpbInPrepay.AssociateEntity#inMainInfo}
     */
    private String inpatientNo;

    /**
     * 发生序号
     */
    private Integer happenNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 预交金额
     */
    private Double prepayCost;

    /**
     * 支付方式
     */
    private PayWayEnum payWay;

    /**
     * 科室代码
     */
    private String deptCode;

    /**
     * 预交金收据号码
     */
    private String receiptNo;

    /**
     * 统计日期
     */
    private Date statDate;

    /**
     * 结算时间
     */
    private Date balanceDate;

    /**
     * 结算标志 0:未结算；1:已结算 2:已结转
     */
    private BalanceStateEnum balanceState;

    /**
     * 预交金状态
     */
    private PrepayStateEnum prepayState;

    /**
     * 原票据号
     */
    private String oldRecipeNo;

    /**
     * 开户银行
     */
    private String openBank;

    /**
     * 开户帐户
     */
    private String openAccounts;

    /**
     * 结算发票号
     */
    private String invoiceNo;

    /**
     * 结算序号
     */
    private Integer balanceNo;

    /**
     * 结算人代码
     */
    private String balanceOperCode;

    /**
     * 上缴标志（1是 0否）
     */
    private Boolean reportFlag;

    /**
     * 审核序号
     */
    private String checkNo;

    /**
     * 财务组代码
     */
    private String finFrpCode;

    /**
     * 工作单位
     */
    private String workName;

    /**
     * 0非转押金，1转押金，2转押金已打印
     */
    private String transFlag;

    /**
     * 转押金时结算序号
     */
    private Integer changeBalanceNo;

    /**
     * 转押金结算员
     */
    private String transCode;

    /**
     * 转押金时间
     */
    private Date transDate;

    /**
     * 打印标志
     */
    private Boolean printFlag;

    /**
     * 正常收取 1 结算召回 2
     */
    private String extFlag;

    /**
     * 日结标志 0未日结 1日结
     */
    private String ext1Flag;

    /**
     * pos交易流水号或支票号或汇票号
     */
    private String posTransNo;

    /**
     * 操作员
     */
    private String operCode;

    /**
     * 操作日期
     */
    private Date operDate;

    /**
     * 操作员科室
     */
    private String operDeptCode;

    /**
     * 存交易的原始信息
     */
    private String memo;

    /**
     * 职员院区标识
     */
    private DeptOwnEnum deptOwn;

    /**
     * 职员院区标识
     */
    private DeptOwnEnum employeeOwn;

    /**
     * 自助机交易订单号
     */
    private String referNum;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIpbInPrepay) {
            var that = (FinIpbInPrepay) arg0;
            return StringUtils.equals(this.inpatientNo, that.inpatientNo)
                    && IntegerUtils.equals(this.balanceNo, that.balanceNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(inpatientNo, balanceNo);
    }

    /**
     * 预交金支付方式字典
     */
    @Getter
    @AllArgsConstructor
    public enum PayWayEnum implements Enum {
        现金("CA", "现金"),
        支票("CH", "支票"),
        信用卡("CD", "信用卡"),
        借记卡("DB", "借记卡"),
        转押金("AJ", "转押金"),
        汇票("PO", "汇票"),
        保险帐户("PS", "保险帐户"),
        院内账户("YS", "院内账户"),
        东风卡("DFK", "东风卡"),
        日间手术账户("RJSH", "日间手术账户");
    
        /**
         * 数据库存值
         */
        private String value;
    
        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 预交金状态字典
     */
    @Getter
    @AllArgsConstructor
    public enum PrepayStateEnum implements Enum {
        收取("0", "收取"),
        作废("1", "作废"),
        补打("2", "补打"),
        结算召回作废("3", "结算召回作废");
    
        /**
         * 数据库存值
         */
        private String value;
    
        /**
         * 描述存值
         */
        private String description;
    }
}
