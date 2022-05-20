package com.kaos.skynet.api.data.entity.outpatient.fee.balance.invoice;

import java.util.Date;

import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.enums.common.PayKindEnum;
import com.kaos.skynet.api.enums.common.PayModeEnum;
import com.kaos.skynet.api.enums.common.TransTypeEnum;

import lombok.Data;

/**
 * 发票信息表(结算信息表)
 */
@Data
public class FinOpbInvoiceInfo {
    /**
     * 发票号 {@code 主键}
     */
    private String invoiceNo = null;

    /**
     * 交易类型 {@code 主键}
     */
    private TransTypeEnum transType = null;

    /**
     * 发票序号，一次结算产生多张发票的combNo {@code 主键}
     */
    private String invoiceSeq = null;

    /**
     * 就诊卡号
     */
    private String cardNo = null;

    /**
     * 挂号日期
     */
    private Date regDate = null;

    /**
     * 患者姓名
     */
    private String name = null;

    /**
     * 结算类别代码
     */
    private PayKindEnum payKindCode = null;

    /**
     * 合同单位编码
     */
    private String pactCode = null;

    /**
     * 合同单位名称
     */
    private String pactName = null;

    /**
     * 医保卡号
     */
    private String medCardNo = null;

    /**
     * 医疗类别
     */
    private String medicalType = null;

    /**
     * 总额
     */
    private Double totCost = null;

    /**
     * 统筹金额
     */
    private Double pubCost = null;

    /**
     * 自费金额
     */
    private Double ownCost = null;

    /**
     * 自付金额
     */
    private Double payCost = null;

    /**
     * 预留1
     */
    private String back1 = null;

    /**
     * 预留2(用于存收费时实际收的现金包含找零)
     */
    private String back2 = null;

    /**
     * 预留保存退费原因(需要统计)
     */
    private String back3 = null;

    /**
     * 实付金额
     */
    private Double realCost = null;

    /**
     * 结算人
     */
    private String operCode = null;

    /**
     * 结算时间
     */
    private Date operDate = null;

    /**
     * 0不是体检/1个人体检/2团体体检
     */
    private String examineFlag = null;

    /**
     * "0" 退费 "1" 有效 "2" 重打 "3" 注销
     */
    private String cancelFlag = null;

    /**
     * 作废票据号
     */
    private String cancelInvoice = null;

    /**
     * 作废人
     */
    private String cancelOperCode = null;

    /**
     * 作废时间
     */
    private String cancelOperDate = null;

    /**
     * 检查标志
     */
    private Boolean checkFlag = null;

    /**
     * 检查人
     */
    private String checkOperCode = null;

    /**
     * 检查时间
     */
    private String checkOperDate = null;

    /**
     * 日结标识
     */
    private Boolean balanceFlag = null;

    /**
     * 日结标识号
     */
    private String balanceNo = null;

    /**
     * 日结人
     */
    private String balanceOperCode = null;

    /**
     * 日结时间
     */
    private String balanceOperDate = null;

    /**
     * 作废（扩展标志 1 自费 2 记帐 3 特殊） 0未打印 1重打标记
     */
    private String extFlag = null;

    /**
     * 挂号流水号
     */
    private String clinicCode = null;

    /**
     * 实际发票打印号码
     */
    private String printInvoiceNo = null;

    /**
     * 本张发票发药窗口信息
     */
    private String drugWindow = null;

    /**
     * 是否账户集中打印发票
     */
    private Boolean accountFlag = null;

    /**
     * 天津发票重打页联编码（勿删）
     */
    private String printNum = null;

    /**
     * 一次结算收费序号
     */
    private String balanceComboNo = null;

    /**
     * 院区
     */
    private DeptOwnEnum deptOwn = null;

    /**
     * 职工院区
     */
    private DeptOwnEnum emplDeptOwn = null;

    /**
     * 
     */
    private PayModeEnum payMode = null;

    /**
     * 绿色通道0正常, 1 绿色通道未实际缴费，2 绿色通道已缴费
     */
    private String greenRoad = null;

    /**
     * 重打操作员
     */
    private String reprintOperCode = null;

    /**
     * 重打时间
     */
    private Date reprintDate = null;

    /**
     * IP
     */
    private String ip = null;

    /**
     * 诊间支付操作员
     */
    private String payOperCode = null;

    /**
     * 客户端流水号
     */
    private String clientOrderId = null;
}
