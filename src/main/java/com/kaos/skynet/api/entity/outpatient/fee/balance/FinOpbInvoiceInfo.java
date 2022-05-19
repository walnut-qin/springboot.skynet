package com.kaos.skynet.api.entity.outpatient.fee.balance;

import java.util.Date;

import com.kaos.skynet.api.entity.common.ComPatientInfo;
import com.kaos.skynet.api.entity.common.DawnOrgEmpl;
import com.kaos.skynet.api.entity.outpatient.FinOprRegister;
import com.kaos.skynet.enums.common.DeptOwnEnum;
import com.kaos.skynet.enums.common.PayKindEnum;
import com.kaos.skynet.enums.common.PayModeEnum;
import com.kaos.skynet.enums.common.TransTypeEnum;

/**
 * 发票信息表(结算信息表)
 */
public class FinOpbInvoiceInfo {
    /**
     * 发票号 {@code 主键}
     */
    public String invoiceNo = null;

    /**
     * 交易类型 {@code 主键}
     */
    public TransTypeEnum transType = null;

    /**
     * 发票序号，一次结算产生多张发票的combNo {@code 主键}
     */
    public String invoiceSeq = null;

    /**
     * 就诊卡号 {@link FinOpbInvoiceInfo.AssociateEntity#patientInfo}
     */
    public String cardNo = null;

    /**
     * 挂号日期
     */
    public Date regDate = null;

    /**
     * 患者姓名
     */
    public String name = null;

    /**
     * 结算类别代码
     */
    public PayKindEnum payKindCode = null;

    /**
     * 合同单位编码
     */
    public String pactCode = null;

    /**
     * 合同单位名称
     */
    public String pactName = null;

    /**
     * 医保卡号
     */
    public String medCardNo = null;

    /**
     * 医疗类别
     */
    public String medicalType = null;

    /**
     * 总额
     */
    public Double totCost = null;

    /**
     * 统筹金额
     */
    public Double pubCost = null;

    /**
     * 自费金额
     */
    public Double ownCost = null;

    /**
     * 自付金额
     */
    public Double payCost = null;

    /**
     * 预留1
     */
    public String back1 = null;

    /**
     * 预留2(用于存收费时实际收的现金包含找零)
     */
    public String back2 = null;

    /**
     * 预留保存退费原因(需要统计)
     */
    public String back3 = null;

    /**
     * 实付金额
     */
    public Double realCost = null;

    /**
     * 结算人 {@link FinOpbInvoiceInfo.AssociateEntity#operEmpl}
     */
    public String operCode = null;

    /**
     * 结算时间
     */
    public Date operDate = null;

    /**
     * 0不是体检/1个人体检/2团体体检
     */
    public String examineFlag = null;

    /**
     * "0" 退费 "1" 有效 "2" 重打 "3" 注销
     */
    public String cancelFlag = null;

    /**
     * 作废票据号
     */
    public String cancelInvoice = null;

    /**
     * 作废人 {@link FinOpbInvoiceInfo.AssociateEntity#cancelOperEmpl}
     */
    public String cancelOperCode = null;

    /**
     * 作废时间
     */
    public String cancelOperDate = null;

    /**
     * 检查标志
     */
    public Boolean checkFlag = null;

    /**
     * 检查人 {@link FinOpbInvoiceInfo.AssociateEntity#checkOperEmpl}
     */
    public String checkOperCode = null;

    /**
     * 检查时间
     */
    public String checkOperDate = null;

    /**
     * 日结标识
     */
    public Boolean balanceFlag = null;

    /**
     * 日结标识号
     */
    public String balanceNo = null;

    /**
     * 日结人 {@link FinOpbInvoiceInfo.AssociateEntity#balanceOperEmpl}
     */
    public String balanceOperCode = null;

    /**
     * 日结时间
     */
    public String balanceOperDate = null;

    /**
     * 作废（扩展标志 1 自费 2 记帐 3 特殊） 0未打印 1重打标记
     */
    public String extFlag = null;

    /**
     * 挂号流水号 {@link FinOpbInvoiceInfo.AssociateEntity#register}
     */
    public String clinicCode = null;

    /**
     * 实际发票打印号码
     */
    public String printInvoiceNo = null;

    /**
     * 本张发票发药窗口信息
     */
    public String drugWindow = null;

    /**
     * 是否账户集中打印发票
     */
    public Boolean accountFlag = null;

    /**
     * 天津发票重打页联编码（勿删）
     */
    public String printNum = null;

    /**
     * 一次结算收费序号
     */
    public String balanceComboNo = null;

    /**
     * 院区
     */
    public DeptOwnEnum deptOwn = null;

    /**
     * 职工院区
     */
    public DeptOwnEnum emplDeptOwn = null;

    /**
     * 
     */
    public PayModeEnum payMode = null;

    /**
     * 绿色通道0正常, 1 绿色通道未实际缴费，2 绿色通道已缴费
     */
    public String greenRoad = null;

    /**
     * 重打操作员 {@link FinOpbInvoiceInfo.AssociateEntity#reprintOperEmpl}
     */
    public String reprintOperCode = null;

    /**
     * 重打时间
     */
    public Date reprintDate = null;

    /**
     * IP
     */
    public String ip = null;

    /**
     * 诊间支付操作员 {@link FinOpbInvoiceInfo.AssociateEntity#payOperEmpl}
     */
    public String payOperCode = null;

    /**
     * 客户端流水号
     */
    public String clientOrderId = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 患者基本信息 {@link FinOpbInvoiceInfo#cardNo}
         */
        public ComPatientInfo patientInfo = null;

        /**
         * 结算人 {@link FinOpbInvoiceInfo#operCode}
         */
        public DawnOrgEmpl operEmpl = null;

        /**
         * 作废人 {@link FinOpbInvoiceInfo#cancelOperCode}
         */
        public DawnOrgEmpl cancelOperEmpl = null;

        /**
         * 核查人 {@link FinOpbInvoiceInfo#checkOperCode}
         */
        public DawnOrgEmpl checkOperEmpl = null;

        /**
         * 日结员 {@link FinOpbInvoiceInfo#balanceOperCode}
         */
        public DawnOrgEmpl balanceOperEmpl = null;

        /**
         * 日结员 {@link FinOpbInvoiceInfo#clinicCode}
         */
        public FinOprRegister register = null;

        /**
         * 重打操作员 {@link FinOpbInvoiceInfo#reprintOperCode}
         */
        public DawnOrgEmpl reprintOperEmpl = null;

        /**
         * 重打操作员 {@link FinOpbInvoiceInfo#payOperCode}
         */
        public DawnOrgEmpl payOperEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
