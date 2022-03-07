package com.kaos.skynet.entity.inpatient.fee;

import java.util.Date;

import com.kaos.skynet.entity.common.DawnOrgDept;
import com.kaos.skynet.entity.common.DawnOrgEmpl;
import com.kaos.skynet.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.entity.inpatient.surgery.MetOpsApply;
import com.kaos.skynet.enums.impl.common.DeptOwnEnum;
import com.kaos.skynet.enums.impl.common.MinFeeEnum;
import com.kaos.skynet.enums.impl.common.PayKindEnum;
import com.kaos.skynet.enums.impl.common.TransTypeEnum;
import com.kaos.skynet.enums.impl.inpatient.fee.balance.BalanceStateEnum;
import com.kaos.skynet.enums.impl.pharmacy.DrugQualityEnum;
import com.kaos.skynet.enums.impl.pharmacy.DrugTypeEnum;

public class FinIpbMedicineList {
    /**
     * 处方号
     */
    public String recipeNo = null;

    /**
     * 处方内项目流水号
     */
    public Integer sequenceNo = null;

    /**
     * 交易类型,1正交易，2反交易
     */
    public TransTypeEnum transType = null;

    /**
     * 住院号 {@link FinIpbMedicineList.AssociateEntity#inMainInfo}
     */
    public String inpatientNo = null;

    /**
     * 姓名
     */
    public String name = null;

    /**
     * 结算类别 01-自费 02-保险 03-公费在职 04-公费退休 05-公费高干
     */
    public PayKindEnum payKind = null;

    /**
     * 合同单位
     */
    public String pactCode = null;

    /**
     * 在院科室代码 {@link FinIpbMedicineList.AssociateEntity#inHosDept}
     */
    public String inHosDeptCode = null;

    /**
     * 护士站代码 {@link FinIpbMedicineList.AssociateEntity#nurseCell}
     */
    public String nurseCellCode = null;

    /**
     * 开立医生所属科室代码 {@link FinIpbMedicineList.AssociateEntity#recipeDept}
     */
    public String recipeDeptCode = null;

    /**
     * 执行科室代码 {@link FinIpbMedicineList.AssociateEntity#executeDept}
     */
    public String executeDeptCode = null;

    /**
     * 扣库科室代码 {@link FinIpbMedicineList.AssociateEntity#medicineDept}
     */
    public String medicineDeptCode = null;

    /**
     * 开立医师代码 {@link FinIpbMedicineList.AssociateEntity#recipeDoc}
     */
    public String recipeDocCode = null;

    /**
     * 药品代码
     */
    public String drugCode = null;

    /**
     * 最小费用编码
     */
    public MinFeeEnum feeCode = null;

    /**
     * 中心代码
     */
    public String centerCode = null;

    /**
     * 药品名称
     */
    public String drugName = null;

    /**
     * 规格
     */
    public String specs = null;

    /**
     * 药品类型
     */
    public DrugTypeEnum drugType = null;

    /**
     * 药品质量
     */
    public DrugQualityEnum drugQuality = null;

    /**
     * 自制标识
     */
    public Boolean homeMadeFlag = null;

    /**
     * 单价
     */
    public Double unitPrice = null;

    /**
     * 当前单位
     */
    public String currentUnit = null;

    /**
     * 包装数
     */
    public Double packQty = null;

    /**
     * 数量
     */
    public Double qty = null;

    /**
     * 付数
     */
    public Integer days = null;

    /**
     * 费用金额
     */
    public Double totCost = null;

    /**
     * 自费金额
     */
    public Double ownCost = null;

    /**
     * 账户金额
     */
    public Double payCost = null;

    /**
     * 统筹金额
     */
    public Double pubCost = null;

    /**
     * 优惠金额
     */
    public Double ecoCost = null;

    /**
     * 更新库存的流水号(物资)
     */
    public Integer updateSequenceNo = null;

    /**
     * 出库单序列号
     */
    public String sendMatSequence = null;

    /**
     * 发药状态（0 划价 2摆药 1批费）
     */
    public String sendFlag = null;

    /**
     * 是否婴儿
     */
    public Boolean babyFlag = null;

    /**
     * 急诊抢救标志
     */
    public Boolean jzqjFlag = null;

    /**
     * 出院带疗标记 0 否 1 是 ?
     */
    public String broughtFlag = null;

    /**
     * 扩展标志(公费患者是否使用了自费的项目0否,1是)
     */
    public Boolean extFlag = null;

    /**
     * 结算发票号
     */
    public String invoiceNo = null;

    /**
     * 结算序号
     */
    public Integer balanceNo = null;

    /**
     * 结算状态
     */
    public BalanceStateEnum balanceState = null;

    /**
     * 可退数量
     */
    public Double noBackNum = null;

    /**
     * 扩展代码(中山一：保存退费原记录的处方号)
     */
    public String extCode = null;

    /**
     * 扩展操作员 {@link FinIpbMedicineList.AssociateEntity#extOperEmpl}
     */
    public String extOperCode = null;

    /**
    *
    */
    public Date extDate = null;

    /**
     * 审批号(中山一：退费时保存退费申请单号)
     */
    public String apprNo = null;

    /**
     * 划价人 {@link FinIpbMedicineList.AssociateEntity#chargeOperEmpl}
     */
    public String chargeOperCode = null;

    /**
     * 划价时间
     */
    public Date chargeDate = null;

    /**
     * 计费人 {@link FinIpbMedicineList.AssociateEntity#feeOperEmpl}
     */
    public String feeOperCode = null;

    /**
     * 计费时间
     */
    public Date feeDate = null;

    /**
     * 执行人代码 {@link FinIpbMedicineList.AssociateEntity#execOperEmpl}
     */
    public String execOperCode = null;

    /**
     * 执行日期
     */
    public Date execDate = null;

    /**
     * 执行人代码 {@link FinIpbMedicineList.AssociateEntity#execOperEmpl}
     */
    public String sendDrugOperCode = null;

    /**
     * 发药日期
     */
    public Date sendDrugDate = null;

    /**
     * 审核人 {@link FinIpbMedicineList.AssociateEntity#checkOperEmpl}
     */
    public String checkOperCode = null;

    /**
     * 审核序号
     */
    public String checkNo = null;

    /**
     * 医嘱流水号
     */
    public String moOrder = null;

    /**
     * 医嘱执行单流水号
     */
    public String moExecSqn = null;

    /**
     * 收费比率
     */
    public Double feeRate = null;

    /**
     * 收费员科室 {@link FinIpbMedicineList.AssociateEntity#feeOperDept}
     */
    public String feeOperDeptCode = null;

    /**
     * 上传标志
     */
    public Boolean uploadFlag = null;

    /**
     * 扩展标志1
     */
    public String extFlag1 = null;

    /**
     * 扩展标志2(收费方式0住院处直接收费,1护士站医嘱收费,2确认收费,3身份变更,4比例调整) 5 终端确认收费 6终端取消 8pacs分诊计费
     * 9pacs退费
     */
    public String extFlag2 = null;

    /**
     * 聊城市医保新增(记录凭单号)
     */
    public String extFlag3 = null;

    /**
     * 医疗组
     */
    public String medicalTeamCode = null;

    /**
     * 手术编码 {@link FinIpbMedicineList.AssociateEntity#surgery}
     */
    public String operationNo = null;

    /**
     * 医保交易流水号,是否报销(1未审核 2报销 3不予报销)
     */
    public String transactionSequenceNumber = null;

    /**
     * 医保交易时间
     */
    public Date siTransactionDateTime = null;

    /**
     * HIS处方号
     */
    public String hisRecipeNo = null;

    /**
     * 医保处方号
     */
    public String siRecipeNo = null;

    /**
     * HIS原处方号
     */
    public String hisCancelRecipeNo = null;

    /**
     * 医保原处方号
     */
    public String siCancelRecipeNo = null;

    /**
     * 扩展标志4(襄阳项目用于费用发生时候患者所在科室)
     */
    public String extFlag4 = null;

    /**
     * 扩展标志5(天津项目用于结转类型 1 正常 0 欠费)
     */
    public String extFlag5 = null;

    /**
     * 主治医生 {@link FinIpbMedicineList.AssociateEntity#chargeDoc}
     */
    public String chargeDocCode = null;

    /**
     * 主治医生所属医疗组
     */
    public String chargeMedicalTeam = null;

    /**
     * MEDICAL_TYPE
     */
    public String localExtFlag1 = null;

    /**
     * 自费标识
     */
    public String localExtFlag2 = null;

    /**
     * MEDICAL_STATE，0未结算，1前置机结算，2费用上传
     */
    public String localExtFlag3 = null;

    /**
     * 扩展标志（襄阳项目用于费用发生时候患者管床医生）
     */
    public String localExtFlag4 = null;

    /**
     * HIS原处方内部流水号
     */
    public String hisCanCelRecipeSequenceNo = null;

    /**
     * 药品批次号
     */
    public String groupCode = null;

    /**
     * 直接收费长期记账标识
     */
    public Boolean longFlag = null;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    public DeptOwnEnum deptOwn = null;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    public DeptOwnEnum employeeOwn = null;

    /**
     * 院外费用发票
     */
    public String otInvoiceNo = null;

    /**
     * 静配中心标识是否允许退费标记0不允许1允许
     */
    public Boolean jpFlag = null;

    /**
     * 日期
     */
    public Date jpDate = null;

    /**
     * 核对员 {@link FinIpbMedicineList.AssociateEntity#jpOperEmpl}
     */
    public String jpOperCode = null;

    /**
     * 上传操作员 {@link FinIpbMedicineList.AssociateEntity#uploadOperEmpl}
     */
    public String uploadOperCode = null;

    /**
     * 上传时间
     */
    public Date uploadDate = null;

    /**
     * 上传IP
     */
    public String uploadIP = null;

    /**
     * 自付比例（医保费用上传出参，用于计算甲乙类费用）liubinglin20210827
     */
    public Double selfPayProp = null;

    /**
     * 全自费金额（医保费用上传出参）liubinglin20210827
     */
    public String fulAmtOwnPayAmt = null;

    /**
     * 医保费用代码类别 liubinglin20210827
     */
    public String centerFeeCode = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 住院实体 {@link FinIpbMedicineList#inpatientNo}
         */
        public FinIprInMainInfo inMainInfo = null;

        /**
         * 住院科室 {@link FinIpbMedicineList#inHosDeptCode}
         */
        public DawnOrgDept inHosDept = null;

        /**
         * 病区 {@link FinIpbMedicineList#nurseCellCode}
         */
        public DawnOrgDept nurseCell = null;

        /**
         * 开立医师所属科室 {@link FinIpbMedicineList#recipeDeptCode}
         */
        public DawnOrgDept recipeDept = null;

        /**
         * 执行科室 {@link FinIpbMedicineList#executeDeptCode}
         */
        public DawnOrgDept executeDept = null;

        /**
         * 扣库科室 {@link FinIpbMedicineList#medicineDeptCode}
         */
        public DawnOrgDept medicineDept = null;

        /**
         * 开立医师 {@link FinIpbMedicineList#recipeDocCode}
         */
        public DawnOrgEmpl recipeDoc = null;

        /**
         * 扩展操作员 {@link FinIpbMedicineList#extOperCode}
         */
        public DawnOrgEmpl extOperEmpl = null;

        /**
         * 划价人 {@link FinIpbMedicineList#chargeOperCode}
         */
        public DawnOrgEmpl chargeOperEmpl = null;

        /**
         * 划价人 {@link FinIpbMedicineList#execOperCode}
         */
        public DawnOrgEmpl execOperEmpl = null;

        /**
         * 划价人 {@link FinIpbMedicineList#sendDrugOperCode}
         */
        public DawnOrgEmpl sendDrugOperEmpl = null;

        /**
         * 划价人 {@link FinIpbMedicineList#feeOperCode}
         */
        public DawnOrgEmpl feeOperEmpl = null;

        /**
         * 划价人 {@link FinIpbMedicineList#checkOperCode}
         */
        public DawnOrgEmpl checkOperEmpl = null;

        /**
         * 划价人 {@link FinIpbMedicineList#feeOperDeptCode}
         */
        public DawnOrgDept feeOperDept = null;

        /**
         * 划价人 {@link FinIpbMedicineList#operationNo}
         */
        public MetOpsApply surgery = null;

        /**
         * 划价人 {@link FinIpbMedicineList#chargeDocCode}
         */
        public DawnOrgEmpl chargeDoc = null;

        /**
         * 划价人 {@link FinIpbMedicineList#jpOperCode}
         */
        public DawnOrgEmpl jpOperEmpl = null;

        /**
         * 划价人 {@link FinIpbMedicineList#uploadOperCode}
         */
        public DawnOrgEmpl uploadOperEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
