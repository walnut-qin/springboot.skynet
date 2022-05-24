package com.kaos.skynet.api.data.entity.outpatient.fee.balance.invoice;

import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.enums.common.PayKindEnum;
import com.kaos.skynet.api.enums.common.PayModeEnum;
import com.kaos.skynet.api.enums.common.TransTypeEnum;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Data;

/**
 * 发票信息表(结算信息表)
 */
@Data
@Builder
public class FinOpbInvoiceInfo {
    /**
     * 发票号 {@code 主键}
     */
    private String invoiceNo;

    /**
     * 交易类型 {@code 主键}
     */
    private TransTypeEnum transType;

    /**
     * 发票序号，一次结算产生多张发票的combNo {@code 主键}
     */
    private String invoiceSeq;

    /**
     * 就诊卡号
     */
    private String cardNo;

    /**
     * 挂号日期
     */
    private Date regDate;

    /**
     * 患者姓名
     */
    private String name;

    /**
     * 结算类别代码
     */
    private PayKindEnum payKindCode;

    /**
     * 合同单位编码
     */
    private String pactCode;

    /**
     * 合同单位名称
     */
    private String pactName;

    /**
     * 医保卡号
     */
    private String medCardNo;

    /**
     * 医疗类别
     */
    private String medicalType;

    /**
     * 总额
     */
    private Double totCost;

    /**
     * 统筹金额
     */
    private Double pubCost;

    /**
     * 自费金额
     */
    private Double ownCost;

    /**
     * 自付金额
     */
    private Double payCost;

    /**
     * 预留1
     */
    private String back1;

    /**
     * 预留2(用于存收费时实际收的现金包含找零)
     */
    private String back2;

    /**
     * 预留保存退费原因(需要统计)
     */
    private String back3;

    /**
     * 实付金额
     */
    private Double realCost;

    /**
     * 结算人
     */
    private String operCode;

    /**
     * 结算时间
     */
    private Date operDate;

    /**
     * 0不是体检/1个人体检/2团体体检
     */
    private String examineFlag;

    /**
     * "0" 退费 "1" 有效 "2" 重打 "3" 注销
     */
    private String cancelFlag;

    /**
     * 作废票据号
     */
    private String cancelInvoice;

    /**
     * 作废人
     */
    private String cancelOperCode;

    /**
     * 作废时间
     */
    private String cancelOperDate;

    /**
     * 检查标志
     */
    private Boolean checkFlag;

    /**
     * 检查人
     */
    private String checkOperCode;

    /**
     * 检查时间
     */
    private String checkOperDate;

    /**
     * 日结标识
     */
    private Boolean balanceFlag;

    /**
     * 日结标识号
     */
    private String balanceNo;

    /**
     * 日结人
     */
    private String balanceOperCode;

    /**
     * 日结时间
     */
    private String balanceOperDate;

    /**
     * 作废（扩展标志 1 自费 2 记帐 3 特殊） 0未打印 1重打标记
     */
    private String extFlag;

    /**
     * 挂号流水号
     */
    private String clinicCode;

    /**
     * 实际发票打印号码
     */
    private String printInvoiceNo;

    /**
     * 本张发票发药窗口信息
     */
    private String drugWindow;

    /**
     * 是否账户集中打印发票
     */
    private Boolean accountFlag;

    /**
     * 天津发票重打页联编码（勿删）
     */
    private String printNum;

    /**
     * 一次结算收费序号
     */
    private String balanceComboNo;

    /**
     * 院区
     */
    private DeptOwnEnum deptOwn;

    /**
     * 职工院区
     */
    private DeptOwnEnum emplDeptOwn;

    /**
     * 
     */
    private PayModeEnum payMode;

    /**
     * 绿色通道0正常, 1 绿色通道未实际缴费，2 绿色通道已缴费
     */
    private String greenRoad;

    /**
     * 重打操作员
     */
    private String reprintOperCode;

    /**
     * 重打时间
     */
    private Date reprintDate;

    /**
     * IP
     */
    private String ip;

    /**
     * 诊间支付操作员
     */
    private String payOperCode;

    /**
     * 客户端流水号
     */
    private String clientOrderId;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinOpbInvoiceInfo) {
            var that = (FinOpbInvoiceInfo) arg0;
            return StringUtils.equals(this.invoiceNo, that.invoiceNo) 
                && this.transType == that.transType
                && StringUtils.equals(this.invoiceSeq, that.invoiceSeq);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(invoiceNo, transType, invoiceSeq);
    }
}
