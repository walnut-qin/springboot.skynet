package com.kaos.skynet.api.data.entity.inpatient.fee;

import java.util.Date;

import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.MinFeeEnum;
import com.kaos.skynet.api.enums.common.PayKindEnum;
import com.kaos.skynet.api.enums.common.TransTypeEnum;
import com.kaos.skynet.api.enums.inpatient.fee.balance.BalanceStateEnum;
import com.kaos.skynet.api.enums.pharmacy.DrugQualityEnum;
import com.kaos.skynet.api.enums.pharmacy.DrugTypeEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FinIpbMedicineList {
    /**
     * 处方号
     */
    private String recipeNo;

    /**
     * 处方内项目流水号
     */
    private Integer sequenceNo;

    /**
     * 交易类型,1正交易，2反交易
     */
    private TransTypeEnum transType;

    /**
     * 住院号 {@link FinIpbMedicineList.AssociateEntity#inMainInfo}
     */
    private String inpatientNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 结算类别 01-自费 02-保险 03-公费在职 04-公费退休 05-公费高干
     */
    private PayKindEnum payKind;

    /**
     * 合同单位
     */
    private String pactCode;

    /**
     * 在院科室代码 {@link FinIpbMedicineList.AssociateEntity#inHosDept}
     */
    private String inHosDeptCode;

    /**
     * 护士站代码 {@link FinIpbMedicineList.AssociateEntity#nurseCell}
     */
    private String nurseCellCode;

    /**
     * 开立医生所属科室代码 {@link FinIpbMedicineList.AssociateEntity#recipeDept}
     */
    private String recipeDeptCode;

    /**
     * 执行科室代码 {@link FinIpbMedicineList.AssociateEntity#executeDept}
     */
    private String executeDeptCode;

    /**
     * 扣库科室代码 {@link FinIpbMedicineList.AssociateEntity#medicineDept}
     */
    private String medicineDeptCode;

    /**
     * 开立医师代码 {@link FinIpbMedicineList.AssociateEntity#recipeDoc}
     */
    private String recipeDocCode;

    /**
     * 药品代码
     */
    private String drugCode;

    /**
     * 最小费用编码
     */
    private MinFeeEnum feeCode;

    /**
     * 中心代码
     */
    private String centerCode;

    /**
     * 药品名称
     */
    private String drugName;

    /**
     * 规格
     */
    private String specs;

    /**
     * 药品类型
     */
    private DrugTypeEnum drugType;

    /**
     * 药品质量
     */
    private DrugQualityEnum drugQuality;

    /**
     * 自制标识
     */
    private Boolean homeMadeFlag;

    /**
     * 单价
     */
    private Double unitPrice;

    /**
     * 当前单位
     */
    private String currentUnit;

    /**
     * 包装数
     */
    private Double packQty;

    /**
     * 数量
     */
    private Double qty;

    /**
     * 付数
     */
    private Integer days;

    /**
     * 费用金额
     */
    private Double totCost;

    /**
     * 自费金额
     */
    private Double ownCost;

    /**
     * 账户金额
     */
    private Double payCost;

    /**
     * 统筹金额
     */
    private Double pubCost;

    /**
     * 优惠金额
     */
    private Double ecoCost;

    /**
     * 更新库存的流水号(物资)
     */
    private Integer updateSequenceNo;

    /**
     * 出库单序列号
     */
    private String sendMatSequence;

    /**
     * 发药状态（0 划价 2摆药 1批费）
     */
    private String sendFlag;

    /**
     * 是否婴儿
     */
    private Boolean babyFlag;

    /**
     * 急诊抢救标志
     */
    private Boolean jzqjFlag;

    /**
     * 出院带疗标记 0 否 1 是 ?
     */
    private String broughtFlag;

    /**
     * 扩展标志(公费患者是否使用了自费的项目0否,1是)
     */
    private Boolean extFlag;

    /**
     * 结算发票号
     */
    private String invoiceNo;

    /**
     * 结算序号
     */
    private Integer balanceNo;

    /**
     * 结算状态
     */
    private BalanceStateEnum balanceState;

    /**
     * 可退数量
     */
    private Double noBackNum;

    /**
     * 扩展代码(中山一：保存退费原记录的处方号)
     */
    private String extCode;

    /**
     * 扩展操作员 {@link FinIpbMedicineList.AssociateEntity#extOperEmpl}
     */
    private String extOperCode;

    /**
    *
    */
    private Date extDate;

    /**
     * 审批号(中山一：退费时保存退费申请单号)
     */
    private String apprNo;

    /**
     * 划价人 {@link FinIpbMedicineList.AssociateEntity#chargeOperEmpl}
     */
    private String chargeOperCode;

    /**
     * 划价时间
     */
    private Date chargeDate;

    /**
     * 计费人 {@link FinIpbMedicineList.AssociateEntity#feeOperEmpl}
     */
    private String feeOperCode;

    /**
     * 计费时间
     */
    private Date feeDate;

    /**
     * 执行人代码 {@link FinIpbMedicineList.AssociateEntity#execOperEmpl}
     */
    private String execOperCode;

    /**
     * 执行日期
     */
    private Date execDate;

    /**
     * 执行人代码 {@link FinIpbMedicineList.AssociateEntity#execOperEmpl}
     */
    private String sendDrugOperCode;

    /**
     * 发药日期
     */
    private Date sendDrugDate;

    /**
     * 审核人 {@link FinIpbMedicineList.AssociateEntity#checkOperEmpl}
     */
    private String checkOperCode;

    /**
     * 审核序号
     */
    private String checkNo;

    /**
     * 医嘱流水号
     */
    private String moOrder;

    /**
     * 医嘱执行单流水号
     */
    private String moExecSqn;

    /**
     * 收费比率
     */
    private Double feeRate;

    /**
     * 收费员科室 {@link FinIpbMedicineList.AssociateEntity#feeOperDept}
     */
    private String feeOperDeptCode;

    /**
     * 上传标志
     */
    private Boolean uploadFlag;

    /**
     * 扩展标志1
     */
    private String extFlag1;

    /**
     * 扩展标志2(收费方式0住院处直接收费,1护士站医嘱收费,2确认收费,3身份变更,4比例调整) 5 终端确认收费 6终端取消 8pacs分诊计费
     * 9pacs退费
     */
    private String extFlag2;

    /**
     * 聊城市医保新增(记录凭单号)
     */
    private String extFlag3;

    /**
     * 医疗组
     */
    private String medicalTeamCode;

    /**
     * 手术编码 {@link FinIpbMedicineList.AssociateEntity#surgery}
     */
    private String operationNo;

    /**
     * 医保交易流水号,是否报销(1未审核 2报销 3不予报销)
     */
    private String transactionSequenceNumber;

    /**
     * 医保交易时间
     */
    private Date siTransactionDateTime;

    /**
     * HIS处方号
     */
    private String hisRecipeNo;

    /**
     * 医保处方号
     */
    private String siRecipeNo;

    /**
     * HIS原处方号
     */
    private String hisCancelRecipeNo;

    /**
     * 医保原处方号
     */
    private String siCancelRecipeNo;

    /**
     * 扩展标志4(襄阳项目用于费用发生时候患者所在科室)
     */
    private String extFlag4;

    /**
     * 扩展标志5(天津项目用于结转类型 1 正常 0 欠费)
     */
    private String extFlag5;

    /**
     * 主治医生 {@link FinIpbMedicineList.AssociateEntity#chargeDoc}
     */
    private String chargeDocCode;

    /**
     * 主治医生所属医疗组
     */
    private String chargeMedicalTeam;

    /**
     * MEDICAL_TYPE
     */
    private String localExtFlag1;

    /**
     * 自费标识
     */
    private String localExtFlag2;

    /**
     * MEDICAL_STATE，0未结算，1前置机结算，2费用上传
     */
    private String localExtFlag3;

    /**
     * 扩展标志（襄阳项目用于费用发生时候患者管床医生）
     */
    private String localExtFlag4;

    /**
     * HIS原处方内部流水号
     */
    private String hisCanCelRecipeSequenceNo;

    /**
     * 药品批次号
     */
    private String groupCode;

    /**
     * 直接收费长期记账标识
     */
    private Boolean longFlag;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    private DeptOwnEnum deptOwn;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    private DeptOwnEnum employeeOwn;

    /**
     * 院外费用发票
     */
    private String otInvoiceNo;

    /**
     * 静配中心标识是否允许退费标记0不允许1允许
     */
    private Boolean jpFlag;

    /**
     * 日期
     */
    private Date jpDate;

    /**
     * 核对员 {@link FinIpbMedicineList.AssociateEntity#jpOperEmpl}
     */
    private String jpOperCode;

    /**
     * 上传操作员 {@link FinIpbMedicineList.AssociateEntity#uploadOperEmpl}
     */
    private String uploadOperCode;

    /**
     * 上传时间
     */
    private Date uploadDate;

    /**
     * 上传IP
     */
    private String uploadIP;

    /**
     * 自付比例（医保费用上传出参，用于计算甲乙类费用）liubinglin20210827
     */
    private Double selfPayProp;

    /**
     * 全自费金额（医保费用上传出参）liubinglin20210827
     */
    private String fulAmtOwnPayAmt;

    /**
     * 医保费用代码类别 liubinglin20210827
     */
    private String centerFeeCode;
}
