package com.kaos.skynet.api.data.entity.outpatient.fee;

import java.util.Date;

import com.kaos.skynet.api.enums.common.TradeCodeEnum;
import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 支付方式表（XYHIS.FIN_OPR_PAYMODEL）
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class FinOprPayModel {
    /**
     * 门诊号
     */
    private String clinicCode;

    /**
     * 就诊卡号，若为住院患者，则为住院号
     */
    private String cardNo;

    /**
     * 患者姓名
     */
    private String name;

    /**
     * 操作员编码
     */
    private String operCode;

    /**
     * 操作时间
     */
    private Date operDate;

    /**
     * 支付类型
     */
    private PayTypeEnum payType;

    /**
     * 交易类型
     */
    private TradeCodeEnum tradeCode;

    /**
     * 金额
     */
    private Double amt;

    /**
     * 银行卡号
     */
    private String tranCardNum;

    /**
     * 银行名称
     */
    private String bankNo;

    /**
     * 终端号
     */
    private String termId;

    /**
     * 交易检索参考号
     */
    private String referNum;

    /**
     * 交易终端流水号(用作挂号交易流水号)
     */
    private String traceNum;

    /**
     * 交易日期+时间
     */
    private Date trDateTime;

    /**
     * 交易商户号 （用作挂号交易支付流水号）
     */
    private String merchantNum;

    /**
     * 持卡人姓名
     */
    private String bankName;

    /**
     * 批次号
     */
    private String batchNum;

    /**
     * 卡有效期
     */
    private Date expireDate;

    /**
     * 授权码
     */
    private String authCode;

    /**
     * 原交易检索号
     */
    private String orgReferNum;

    /**
     * 发票号
     */
    private String ext1;

    /**
     * 跑批回写状态(1:跑批成功;0或空:未跑批)
     */
    private String ext2;

    /**
     * 是否跑批(空或0:不跑批;1:需跑批)
     */
    private String ext3;

    /**
     * 1-正交易 2-负交易
     */
    private String ext4;

    /**
     * 1:现场挂号; 2:预约挂号; 3:门诊收费; -3:门诊退费;4:住院收费;负的为相应退号退费
     */
    private String ext5;

    /**
     * 预约表主键
     */
    private String recId;

    /**
     * 流水号
     */
    private String payId;

    /**
     * 银行类型:1-工行 2-招行 3-建行
     */
    private String merchanTag;

    /**
     * 平台交易ID(源启状态回写用)
     */
    private String tranId;

    /**
     * 交易发起方 1：后台跑批 2：窗口 3：自助
     */
    private String placeFlag;

    /**
     * 是否已打印，打印为1
     */
    private Boolean printFlag;

    /**
     * 银医二期当日撤销标记(1:当日撤销)
     */
    private Boolean cancelDay;

    /**
     * 平台结算号
     */
    private String pingTaiJSBH;

    /**
     * 平台结算号回写日期
     */
    private Date pingTaiJSBHDate;

    /**
     * 医院内收退费编号
     */
    private String yuanNeiPayNo;

    /**
     * 医院内收退费时间
     */
    private Date yuanNeiPayTime;

    /**
     * 有效标识，空为有效,2作废
     */
    private String flag;

    /**
     * 作废时间
     */
    private Date flagDate;

    /**
     * 结算操作员
     */
    private String balanceNo;

    @Getter
    @AllArgsConstructor
    public enum PayTypeEnum implements Enum {
        现金0("0", "现金"),
        就诊卡("1", "就诊卡"),
        银行卡("2", "银行卡"),
        支付宝("3", "支付宝"),
        微信("4", "微信"),
        现金5("5", "现金"),
        医保卡6("6", "医保卡"),
        医保卡7("7", "医保卡"),
        其他("9", "其他"),
        建行("10", "建行"),
        weimaiAPP("12", "weimaiAPP"),
        医保线上支付("13", "医保线上支付");
    
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
