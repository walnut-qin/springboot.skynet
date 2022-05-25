package com.kaos.skynet.api.data.entity.outpatient;

import java.time.LocalDateTime;
import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.enums.common.NoonEnum;
import com.kaos.skynet.api.enums.common.PayModeEnum;
import com.kaos.skynet.api.enums.common.TransTypeEnum;
import com.kaos.skynet.api.enums.common.ValidStateEnum;
import com.kaos.skynet.api.enums.outpatient.OutpatientStateEnum;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 门诊挂号实体
 */
@Getter
@Setter
@Builder
public class FinOprRegister {
    /**
     * 门诊号
     */
    private String clinicCode;

    /**
     * 交易类型
     */
    private TransTypeEnum transType;

    /**
     * 就诊卡号
     */
    private String cardNo;

    /**
     * 挂号日期
     */
    private Date regDate;

    /**
     * 午别
     */
    private NoonEnum noon;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idenNo;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 联系电话
     */
    private String relaPhone;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 证件类型
     */
    private String cardType;

    /**
     * 结算类别号
     */
    private String payKindCode;

    /**
     * 结算类别名
     */
    private String payKindName;

    /**
     * 合同号
     */
    private String pactCode;

    /**
     * 合同单位名
     */
    private String pactName;

    /**
     * 医疗证号
     */
    private String medCardNo;

    /**
     * 挂号级别编码
     */
    private String regLevlCode;

    /**
     * 挂号级别名称
     */
    private String regLevlName;

    /**
     * 科室编码 ->
     */
    private String deptCode;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 排班序号
     */
    private String schemaNo;

    /**
     * 每日顺序号
     */
    private String orderNo;

    /**
     * 看诊序号
     */
    private String seeNo;

    /**
     * 看诊开始时间
     */
    private Date beginTime;

    /**
     * 看诊结束时间
     */
    private Date endTime;

    /**
     * 医师编码
     */
    private String doctCode;

    /**
     * 医师姓名
     */
    private String doctName;

    /**
     * 挂号是否收费标志
     */
    private Boolean ynRegCharge;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 处方号
     */
    private String recipeNo;

    /**
     * 0 现场挂号，1 预约挂号，2 特诊挂号
     */
    private String ynBook;

    /**
     * 0 复诊，1 出诊，2 住院复诊
     */
    private String ynFr;

    /**
     * 是否为加号
     */
    private Boolean appenFlag;

    /**
     * 挂号费
     */
    private Double regFee;

    /**
     * 检查费
     */
    private Double checkFee;

    /**
     * 诊察费
     */
    private Double diagFee;

    /**
     * 附加费
     */
    private Double otherFee;

    /**
     * 自费金额
     */
    private Double ownCost;

    /**
     * 统筹金额
     */
    private Double pubCost;

    /**
     * 自付金额
     */
    private Double payCost;

    /**
     * 有效标识
     */
    private ValidStateEnum validFlag;

    /**
     * 操作员编码
     */
    private String operCode;

    /**
     * 操作日期
     */
    private Date operDate;

    /**
     * 作废人
     */
    private String cancelOperCode;

    /**
     * 作废时间
     */
    private String cancelDate;

    /**
     * 医疗类别
     */
    private String medType;

    /**
     * ICD编码
     */
    private String icdCode;

    /**
     * 审批人
     */
    private String examCode;

    /**
     * 审批时间
     */
    private String examDate;

    /**
     * 核查标志
     */
    private Boolean checkFlag;

    /**
     * 核查人
     */
    private String checkOperCode;

    /**
     * 核查时间
     */
    private String checkOperDate;

    /**
     * 日结标志
     */
    private Boolean balanceFlag;

    /**
     * 日结标识号
     */
    private String balanceNo;

    /**
     * 日结员
     */
    private String balanceOperCode;

    /**
     * 日结时间
     */
    private Date balanceDate;

    /**
     * 是否已看诊
     */
    private Boolean ynSee;

    /**
     * 看诊日期
     */
    private Date seeDate;

    /**
     * 分诊标识
     */
    private Boolean triageFlag;

    /**
     * 分诊护士编码
     */
    private String triageOperCode;

    /**
     * 分诊日期
     */
    private Date triageDate;

    /**
     * 发票打印次数
     */
    private Integer invoicePrintCnt;

    /**
     * 看诊科室代码
     */
    private String seeDeptCode;

    /**
     * 看诊医生编码
     */
    private String seeDocCode;

    /**
     * 患者来源，4为互联网门诊患者
     */
    private String inSource;

    /**
     * 是否需要提取病案
     */
    private Boolean sendInHosCaseFlag;

    /**
     * 是否加密姓名
     */
    private Boolean encryptNameFlag;

    /**
     * 密文
     */
    private String normalName;

    /**
     * 留观开始时间
     */
    private Date inDate;

    /**
     * 留观结束时间
     */
    private Date outDate;

    /**
     * 转归代号
     */
    private String zg;

    /**
     * 状态
     */
    private OutpatientStateEnum inState;

    /**
     * 优惠金额
     */
    private Double ecoCost;

    /**
     * 账户挂号标识
     */
    private Boolean isAccount;

    /**
     * 急诊号
     */
    private Boolean emergencyFlag;

    /**
     * 系统自助挂号标志
     */
    private String mark1;

    /**
     * 接诊医生编码
     */
    private String receiptDoctCode;

    /**
     * 接诊时间
     */
    private Date receiptDate;

    /**
     * 接诊状态
     */
    private Boolean receiptFlag;

    /**
     * 门诊一卡通账号
     */
    private String accountNo;

    /**
     * EMR挂号流水号
     */
    private Integer emrRegId;

    /**
     * 健康之路订单号
     */
    private String netCardNo;

    /**
     * 院区标识
     */
    private DeptOwnEnum deptOwn;

    /**
     * 职工院区标识
     */
    private DeptOwnEnum emplOwn;

    /**
     * 号源顺序，分时段挂号
     */
    private Integer schemaQueue;

    /**
     * 支付方式
     */
    private PayModeEnum payMode;

    /**
     * 预约ID
     */
    private String recId;

    /**
     * 支付成功订单号
     */
    private String payOrderNo;

    /**
     * 客户端订单号
     */
    private String clinicOrderId;

    /**
     * 标识已打印
     */
    private Boolean isModifyPrint;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinOprRegister) {
            var that = (FinOprRegister) arg0;
            return StringUtils.equals(this.clinicCode, that.clinicCode)
                    && this.transType == that.transType;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clinicCode, transType);
    }
}
