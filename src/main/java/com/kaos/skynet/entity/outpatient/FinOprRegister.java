package com.kaos.skynet.entity.outpatient;

import java.util.Date;

import com.kaos.skynet.entity.common.ComPatientInfo;
import com.kaos.skynet.entity.common.DawnOrgDept;
import com.kaos.skynet.entity.common.DawnOrgEmpl;
import com.kaos.skynet.enums.impl.common.DeptOwnEnum;
import com.kaos.skynet.enums.impl.common.NoonEnum;
import com.kaos.skynet.enums.impl.common.PayModeEnum;
import com.kaos.skynet.enums.impl.common.SexEnum;
import com.kaos.skynet.enums.impl.common.TransTypeEnum;
import com.kaos.skynet.enums.impl.common.ValidStateEnum;
import com.kaos.skynet.enums.impl.outpatient.OutpatientStateEnum;

/**
 * 门诊挂号实体
 */
public class FinOprRegister {
    /**
     * 门诊号
     */
    public String clinicCode = null;

    /**
     * 交易类型
     */
    public TransTypeEnum transType = null;

    /**
     * 就诊卡号 {@link FinOprRegister.AssociateEntity#patient}
     */
    public String cardNo = null;

    /**
     * 挂号日期
     */
    public Date regDate = null;

    /**
     * 午别
     */
    public NoonEnum noon = null;

    /**
     * 姓名
     */
    public String name = null;

    /**
     * 身份证号
     */
    public String idenNo = null;

    /**
     * 性别
     */
    public SexEnum sex = null;

    /**
     * 生日
     */
    public Date birthday = null;

    /**
     * 联系电话
     */
    public String relaPhone = null;

    /**
     * 家庭住址
     */
    public String address = null;

    /**
     * 证件类型
     */
    public String cardType = null;

    /**
     * 结算类别号
     */
    public String payKindCode = null;

    /**
     * 结算类别名
     */
    public String payKindName = null;

    /**
     * 合同号
     */
    public String pactCode = null;

    /**
     * 合同单位名
     */
    public String pactName = null;

    /**
     * 医疗证号
     */
    public String medCardNo = null;

    /**
     * 挂号级别编码
     */
    public String regLevlCode = null;

    /**
     * 挂号级别名称
     */
    public String regLevlName = null;

    /**
     * 科室编码 -> {@link FinOprRegister.AssociateEntity#regDept}
     */
    public String deptCode = null;

    /**
     * 科室名称
     */
    public String deptName = null;

    /**
     * 排班序号
     */
    public String schemaNo = null;

    /**
     * 每日顺序号
     */
    public String orderNo = null;

    /**
     * 看诊序号
     */
    public String seeNo = null;

    /**
     * 看诊开始时间
     */
    public Date beginTime = null;

    /**
     * 看诊结束时间
     */
    public Date endTime = null;

    /**
     * 医师编码 {@link FinOprRegister.AssociateEntity#doctor}
     */
    public String doctCode = null;

    /**
     * 医师姓名
     */
    public String doctName = null;

    /**
     * 挂号是否收费标志
     */
    public Boolean ynRegCharge = null;

    /**
     * 发票号
     */
    public String invoiceNo = null;

    /**
     * 处方号
     */
    public String recipeNo = null;

    /**
     * 0 现场挂号，1 预约挂号，2 特诊挂号
     */
    public String ynBook = null;

    /**
     * 0 复诊，1 出诊，2 住院复诊
     */
    public String ynFr = null;

    /**
     * 是否为加号
     */
    public Boolean appenFlag = null;

    /**
     * 挂号费
     */
    public Double regFee = null;

    /**
     * 检查费
     */
    public Double checkFee = null;

    /**
     * 诊察费
     */
    public Double diagFee = null;

    /**
     * 附加费
     */
    public Double otherFee = null;

    /**
     * 自费金额
     */
    public Double ownCost = null;

    /**
     * 统筹金额
     */
    public Double pubCost = null;

    /**
     * 自付金额
     */
    public Double payCost = null;

    /**
     * 有效标识
     */
    public ValidStateEnum validFlag = null;

    /**
     * 操作员编码 {@link FinOprRegister.AssociateEntity#regEmpl}
     */
    public String operCode = null;

    /**
     * 操作日期
     */
    public Date operDate = null;

    /**
     * 作废人 {@link FinOprRegister.AssociateEntity#cancelEmpl}
     */
    public String cancelOperCode = null;

    /**
     * 作废时间
     */
    public String cancelDate = null;

    /**
     * 医疗类别
     */
    public String medType = null;

    /**
     * ICD编码
     */
    public String icdCode = null;

    /**
     * 审批人 {@link FinOprRegister.AssociateEntity#examEmpl}
     */
    public String examCode = null;

    /**
     * 审批时间
     */
    public String examDate = null;

    /**
     * 核查标志
     */
    public Boolean checkFlag = null;

    /**
     * 核查人 {@link FinOprRegister.AssociateEntity#checkEmpl}
     */
    public String checkOperCode = null;

    /**
     * 核查时间
     */
    public String checkOperDate = null;

    /**
     * 日结标志
     */
    public Boolean balanceFlag = null;

    /**
     * 日结标识号
     */
    public String balanceNo = null;

    /**
     * 日结员 {@link FinOprRegister.AssociateEntity#balanceEmpl}
     */
    public String balanceOperCode = null;

    /**
     * 日结时间
     */
    public Date balanceDate = null;

    /**
     * 是否已看诊
     */
    public Boolean ynSee = null;

    /**
     * 看诊日期
     */
    public Date seeDate = null;

    /**
     * 分诊标识
     */
    public Boolean triageFlag = null;

    /**
     * 分诊护士编码 {@link FinOprRegister.AssociateEntity#triageEmpl}
     */
    public String triageOperCode = null;

    /**
     * 分诊日期
     */
    public Date triageDate = null;

    /**
     * 发票打印次数
     */
    public Integer invoicePrintCnt = null;

    /**
     * 看诊科室代码 {@link FinOprRegister.AssociateEntity#seeDept}
     */
    public String seeDeptCode = null;

    /**
     * 看诊医生编码 {@link FinOprRegister.AssociateEntity#seeDoct}
     */
    public String seeDocCode = null;

    /**
     * 患者来源，4为互联网门诊患者
     */
    public String inSource = null;

    /**
     * 是否需要提取病案
     */
    public Boolean sendInHosCaseFlag = null;

    /**
     * 是否加密姓名
     */
    public Boolean encryptNameFlag = null;

    /**
     * 密文
     */
    public String normalName = null;

    /**
     * 留观开始时间
     */
    public Date inDate = null;

    /**
     * 留观结束时间
     */
    public Date outDate = null;

    /**
     * 转归代号
     */
    public String zg = null;

    /**
     * 状态
     */
    public OutpatientStateEnum inState = null;

    /**
     * 优惠金额
     */
    public Double ecoCost = null;

    /**
     * 账户挂号标识
     */
    public Boolean isAccount = null;

    /**
     * 急诊号
     */
    public Boolean emergencyFlag = null;

    /**
     * 系统自助挂号标志
     */
    public String mark1 = null;

    /**
     * 接诊医生编码 {@link FinOprRegister.AssociateEntity#receiptDoct}
     */
    public String receiptDoctCode = null;

    /**
     * 接诊时间
     */
    public Date receiptDate = null;

    /**
     * 接诊状态
     */
    public Boolean receiptFlag = null;

    /**
     * 门诊一卡通账号
     */
    public String accountNo = null;

    /**
     * EMR挂号流水号
     */
    public Integer emrRegId = null;

    /**
     * 健康之路订单号
     */
    public String netCardNo = null;

    /**
     * 院区标识
     */
    public DeptOwnEnum deptOwn = null;

    /**
     * 职工院区标识
     */
    public DeptOwnEnum emplOwn = null;

    /**
     * 号源顺序，分时段挂号
     */
    public Integer schemaQueue = null;

    /**
     * 支付方式
     */
    public PayModeEnum payMode = null;

    /**
     * 预约ID
     */
    public String recId = null;

    /**
     * 支付成功订单号
     */
    public String payOrderNo = null;

    /**
     * 客户端订单号
     */
    public String clinicOrderId = null;

    /**
     * 标识已打印
     */
    public Boolean isModifyPrint = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 患者实体 {@link FinOprRegister#cardNo}
         */
        public ComPatientInfo patientInfo = null;

        /**
         * 挂号科室 {@link FinOprRegister#deptCode}
         */
        public DawnOrgDept regDept = null;

        /**
         * 看诊医生 {@link FinOprRegister#doctCode}
         */
        public DawnOrgEmpl doctor = null;

        /**
         * 操作员 {@link FinOprRegister#operCode}
         */
        public DawnOrgEmpl regEmpl = null;

        /**
         * 作废人 {@link FinOprRegister#cancelOperCode}
         */
        public DawnOrgEmpl cancelEmpl = null;

        /**
         * 审批人 {@link FinOprRegister#examCode}
         */
        public DawnOrgEmpl examEmpl = null;

        /**
         * 核查人 {@link FinOprRegister#checkOperCode}
         */
        public DawnOrgEmpl checkEmpl = null;

        /**
         * 日结员 {@link FinOprRegister#balanceOperCode}
         */
        public DawnOrgEmpl balanceEmpl = null;

        /**
         * 分诊护士 {@link FinOprRegister#triageOperCode}
         */
        public DawnOrgEmpl triageEmpl = null;

        /**
         * 看诊科室 {@link FinOprRegister#seeDeptCode}
         */
        public DawnOrgDept seeDept = null;

        /**
         * 看诊医生 {@link FinOprRegister#seeDocCode}
         */
        public DawnOrgEmpl seeDoct = null;

        /**
         * 接诊医生 {@link FinOprRegister#receiptDoctCode}
         */
        public DawnOrgEmpl receiptDoct = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
