package com.kaos.skynet.api.entity.inpatient.fee;

import java.util.Date;

import com.kaos.skynet.api.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.enums.common.DeptOwnEnum;
import com.kaos.skynet.enums.common.PayWayEnum;
import com.kaos.skynet.enums.inpatient.fee.PrepayStateEnum;
import com.kaos.skynet.enums.inpatient.fee.balance.BalanceStateEnum;

public class FinIpbInPrepay {
    /**
     * 住院流水号 {@link FinIpbInPrepay.AssociateEntity#inMainInfo}
     */
    public String inpatientNo = null;

    /**
     * 发生序号
     */
    public Integer happenNo = null;

    /**
     * 姓名
     */
    public String name = null;

    /**
     * 预交金额
     */
    public Double prepayCost = null;

    /**
     * 支付方式
     */
    public PayWayEnum payWay = null;

    /**
     * 科室代码
     */
    public String deptCode = null;

    /**
     * 预交金收据号码
     */
    public String receiptNo = null;

    /**
     * 统计日期
     */
    public Date statDate = null;

    /**
     * 结算时间
     */
    public Date balanceDate = null;

    /**
     * 结算标志 0:未结算；1:已结算 2:已结转
     */
    public BalanceStateEnum balanceState = null;

    /**
     * 预交金状态
     */
    public PrepayStateEnum prepayState = null;

    /**
     * 原票据号
     */
    public String oldRecipeNo = null;

    /**
     * 开户银行
     */
    public String openBank = null;

    /**
     * 开户帐户
     */
    public String openAccounts = null;

    /**
     * 结算发票号
     */
    public String invoiceNo = null;

    /**
     * 结算序号
     */
    public Integer balanceNo = null;

    /**
     * 结算人代码
     */
    public String balanceOperCode = null;

    /**
     * 上缴标志（1是 0否）
     */
    public Boolean reportFlag = null;

    /**
     * 审核序号
     */
    public String checkNo = null;

    /**
     * 财务组代码
     */
    public String finFrpCode = null;

    /**
     * 工作单位
     */
    public String workName = null;

    /**
     * 0非转押金，1转押金，2转押金已打印
     */
    public String transFlag = null;

    /**
     * 转押金时结算序号
     */
    public Integer changeBalanceNo = null;

    /**
     * 转押金结算员
     */
    public String transCode = null;

    /**
     * 转押金时间
     */
    public Date transDate = null;

    /**
     * 打印标志
     */
    public Boolean printFlag = null;

    /**
     * 正常收取 1 结算召回 2
     */
    public String extFlag = null;

    /**
     * 日结标志 0未日结 1日结
     */
    public String ext1Flag = null;

    /**
     * pos交易流水号或支票号或汇票号
     */
    public String posTransNo = null;

    /**
     * 操作员
     */
    public String operCode = null;

    /**
     * 操作日期
     */
    public Date operDate = null;

    /**
     * 操作员科室
     */
    public String operDeptCode = null;

    /**
     * 存交易的原始信息
     */
    public String memo = null;

    /**
     * 职员院区标识
     */
    public DeptOwnEnum deptOwn = null;

    /**
     * 职员院区标识
     */
    public DeptOwnEnum employeeOwn = null;

    /**
     * 自助机交易订单号
     */
    public String referNum = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 住院实体 {@link FinIpbInPrepay#inpatientNo}
         */
        public FinIprInMainInfo inMainInfo = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
