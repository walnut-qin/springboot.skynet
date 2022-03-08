package com.kaos.skynet.entity.outpatient.fee;

import java.util.Date;

import com.kaos.skynet.entity.common.DawnOrgEmpl;
import com.kaos.skynet.enums.impl.common.PayTypeEnum;
import com.kaos.skynet.enums.impl.common.TradeCodeEnum;

/**
 * 支付方式表（XYHIS.FIN_OPR_PAYMODEL）
 */
public class FinOprPayModel {
    /**
     * 门诊号
     */
    public String clinicCode = null;

    /**
     * 就诊卡号，若为住院患者，则为住院号
     */
    public String cardNo = null;

    /**
     * 患者姓名
     */
    public String name = null;

    /**
     * 操作员编码 {@link FinOprPayModel.AssociateEntity#operEmpl}
     */
    public String operCode = null;

    /**
     * 操作时间
     */
    public Date operDate = null;

    /**
     * 支付类型
     */
    public PayTypeEnum payType = null;

    /**
     * 交易类型
     */
    public TradeCodeEnum tradeCode = null;

    /**
     * 金额
     */
    public Double amt = null;

    /**
     * 银行卡号
     */
    public String tranCardNum = null;

    /**
     * 银行名称
     */
    public String bankNo = null;

    /**
     * 终端号
     */
    public String termId = null;

    /**
     * 交易检索参考号
     */
    public String referNum = null;

    /**
     * 交易终端流水号(用作挂号交易流水号)
     */
    public String traceNum = null;

    /**
     * 交易日期+时间
     */
    public Date trDateTime = null;

    /**
     * 交易商户号 （用作挂号交易支付流水号）
     */
    public String merchantNum = null;

    /**
     * 持卡人姓名
     */
    public String bankName = null;

    /**
     * 批次号
     */
    public String batchNum = null;

    /**
     * 卡有效期
     */
    public Date expireDate = null;

    /**
     * 授权码
     */
    public String authCode = null;

    /**
     * 原交易检索号
     */
    public String orgReferNum = null;

    /**
     * 发票号
     */
    public String ext1 = null;

    /**
     * 跑批回写状态(1:跑批成功;0或空:未跑批)
     */
    public String ext2 = null;

    /**
     * 是否跑批(空或0:不跑批;1:需跑批)
     */
    public String ext3 = null;

    /**
     * 1-正交易 2-负交易
     */
    public String ext4 = null;

    /**
     * 1:现场挂号; 2:预约挂号; 3:门诊收费; -3:门诊退费;4:住院收费;负的为相应退号退费
     */
    public String ext5 = null;

    /**
     * 预约表主键
     */
    public String recId = null;

    /**
     * 流水号
     */
    public String payId = null;

    /**
     * 银行类型:1-工行 2-招行 3-建行
     */
    public String merchanTag = null;

    /**
     * 平台交易ID(源启状态回写用)
     */
    public String tranId = null;

    /**
     * 交易发起方 1：后台跑批 2：窗口 3：自助
     */
    public String placeFlag = null;

    /**
     * 是否已打印，打印为1
     */
    public Boolean printFlag = null;

    /**
     * 银医二期当日撤销标记(1:当日撤销)
     */
    public Boolean cancelDay = null;

    /**
     * 平台结算号
     */
    public String pingTaiJSBH = null;

    /**
     * 平台结算号回写日期
     */
    public Date pingTaiJSBHDate = null;

    /**
     * 医院内收退费编号
     */
    public String yuanNeiPayNo = null;

    /**
     * 医院内收退费时间
     */
    public Date yuanNeiPayTime = null;

    /**
     * 有效标识，空为有效,2作废
     */
    public String flag = null;

    /**
     * 作废时间
     */
    public Date flagDate = null;

    /**
     * 结算操作员 {@link FinOprPayModel.AssociateEntity#balanceEmpl}
     */
    public String balanceNo = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 操作员 {@link FinOprPayModel#operCode}
         */
        public DawnOrgEmpl operEmpl = null;

        /**
         * 操作员 {@link FinOprPayModel#balanceNo}
         */
        public DawnOrgEmpl balanceEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
