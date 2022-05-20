package com.kaos.skynet.api.data.entity.outpatient;

import java.time.LocalDateTime;
import java.util.Date;

import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.enums.common.NoonEnum;
import com.kaos.skynet.api.enums.common.PayModeEnum;
import com.kaos.skynet.api.enums.common.TransTypeEnum;
import com.kaos.skynet.api.enums.common.ValidStateEnum;
import com.kaos.skynet.api.enums.outpatient.OutpatientStateEnum;

import lombok.Data;

/**
 * 门诊挂号实体
 */
@Data
public class FinOprRegister {
    /**
     * 门诊号
     */
    private String clinicCode = null;

    /**
     * 交易类型
     */
    private TransTypeEnum transType = null;

    /**
     * 就诊卡号
     */
    private String cardNo = null;

    /**
     * 挂号日期
     */
    private Date regDate = null;

    /**
     * 午别
     */
    private NoonEnum noon = null;

    /**
     * 姓名
     */
    private String name = null;

    /**
     * 身份证号
     */
    private String idenNo = null;

    /**
     * 性别
     */
    private SexEnum sex = null;

    /**
     * 生日
     */
    private LocalDateTime birthday = null;

    /**
     * 联系电话
     */
    private String relaPhone = null;

    /**
     * 家庭住址
     */
    private String address = null;

    /**
     * 证件类型
     */
    private String cardType = null;

    /**
     * 结算类别号
     */
    private String payKindCode = null;

    /**
     * 结算类别名
     */
    private String payKindName = null;

    /**
     * 合同号
     */
    private String pactCode = null;

    /**
     * 合同单位名
     */
    private String pactName = null;

    /**
     * 医疗证号
     */
    private String medCardNo = null;

    /**
     * 挂号级别编码
     */
    private String regLevlCode = null;

    /**
     * 挂号级别名称
     */
    private String regLevlName = null;

    /**
     * 科室编码 ->
     */
    private String deptCode = null;

    /**
     * 科室名称
     */
    private String deptName = null;

    /**
     * 排班序号
     */
    private String schemaNo = null;

    /**
     * 每日顺序号
     */
    private String orderNo = null;

    /**
     * 看诊序号
     */
    private String seeNo = null;

    /**
     * 看诊开始时间
     */
    private Date beginTime = null;

    /**
     * 看诊结束时间
     */
    private Date endTime = null;

    /**
     * 医师编码
     */
    private String doctCode = null;

    /**
     * 医师姓名
     */
    private String doctName = null;

    /**
     * 挂号是否收费标志
     */
    private Boolean ynRegCharge = null;

    /**
     * 发票号
     */
    private String invoiceNo = null;

    /**
     * 处方号
     */
    private String recipeNo = null;

    /**
     * 0 现场挂号，1 预约挂号，2 特诊挂号
     */
    private String ynBook = null;

    /**
     * 0 复诊，1 出诊，2 住院复诊
     */
    private String ynFr = null;

    /**
     * 是否为加号
     */
    private Boolean appenFlag = null;

    /**
     * 挂号费
     */
    private Double regFee = null;

    /**
     * 检查费
     */
    private Double checkFee = null;

    /**
     * 诊察费
     */
    private Double diagFee = null;

    /**
     * 附加费
     */
    private Double otherFee = null;

    /**
     * 自费金额
     */
    private Double ownCost = null;

    /**
     * 统筹金额
     */
    private Double pubCost = null;

    /**
     * 自付金额
     */
    private Double payCost = null;

    /**
     * 有效标识
     */
    private ValidStateEnum validFlag = null;

    /**
     * 操作员编码
     */
    private String operCode = null;

    /**
     * 操作日期
     */
    private Date operDate = null;

    /**
     * 作废人
     */
    private String cancelOperCode = null;

    /**
     * 作废时间
     */
    private String cancelDate = null;

    /**
     * 医疗类别
     */
    private String medType = null;

    /**
     * ICD编码
     */
    private String icdCode = null;

    /**
     * 审批人
     */
    private String examCode = null;

    /**
     * 审批时间
     */
    private String examDate = null;

    /**
     * 核查标志
     */
    private Boolean checkFlag = null;

    /**
     * 核查人
     */
    private String checkOperCode = null;

    /**
     * 核查时间
     */
    private String checkOperDate = null;

    /**
     * 日结标志
     */
    private Boolean balanceFlag = null;

    /**
     * 日结标识号
     */
    private String balanceNo = null;

    /**
     * 日结员
     */
    private String balanceOperCode = null;

    /**
     * 日结时间
     */
    private Date balanceDate = null;

    /**
     * 是否已看诊
     */
    private Boolean ynSee = null;

    /**
     * 看诊日期
     */
    private Date seeDate = null;

    /**
     * 分诊标识
     */
    private Boolean triageFlag = null;

    /**
     * 分诊护士编码
     */
    private String triageOperCode = null;

    /**
     * 分诊日期
     */
    private Date triageDate = null;

    /**
     * 发票打印次数
     */
    private Integer invoicePrintCnt = null;

    /**
     * 看诊科室代码
     */
    private String seeDeptCode = null;

    /**
     * 看诊医生编码
     */
    private String seeDocCode = null;

    /**
     * 患者来源，4为互联网门诊患者
     */
    private String inSource = null;

    /**
     * 是否需要提取病案
     */
    private Boolean sendInHosCaseFlag = null;

    /**
     * 是否加密姓名
     */
    private Boolean encryptNameFlag = null;

    /**
     * 密文
     */
    private String normalName = null;

    /**
     * 留观开始时间
     */
    private Date inDate = null;

    /**
     * 留观结束时间
     */
    private Date outDate = null;

    /**
     * 转归代号
     */
    private String zg = null;

    /**
     * 状态
     */
    private OutpatientStateEnum inState = null;

    /**
     * 优惠金额
     */
    private Double ecoCost = null;

    /**
     * 账户挂号标识
     */
    private Boolean isAccount = null;

    /**
     * 急诊号
     */
    private Boolean emergencyFlag = null;

    /**
     * 系统自助挂号标志
     */
    private String mark1 = null;

    /**
     * 接诊医生编码
     */
    private String receiptDoctCode = null;

    /**
     * 接诊时间
     */
    private Date receiptDate = null;

    /**
     * 接诊状态
     */
    private Boolean receiptFlag = null;

    /**
     * 门诊一卡通账号
     */
    private String accountNo = null;

    /**
     * EMR挂号流水号
     */
    private Integer emrRegId = null;

    /**
     * 健康之路订单号
     */
    private String netCardNo = null;

    /**
     * 院区标识
     */
    private DeptOwnEnum deptOwn = null;

    /**
     * 职工院区标识
     */
    private DeptOwnEnum emplOwn = null;

    /**
     * 号源顺序，分时段挂号
     */
    private Integer schemaQueue = null;

    /**
     * 支付方式
     */
    private PayModeEnum payMode = null;

    /**
     * 预约ID
     */
    private String recId = null;

    /**
     * 支付成功订单号
     */
    private String payOrderNo = null;

    /**
     * 客户端订单号
     */
    private String clinicOrderId = null;

    /**
     * 标识已打印
     */
    private Boolean isModifyPrint = null;
}
