package com.kaos.skynet.api.entity.inpatient.fee;

import java.util.Date;

import com.kaos.skynet.api.entity.common.DawnOrgDept;
import com.kaos.skynet.api.entity.common.DawnOrgEmpl;
import com.kaos.skynet.api.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.enums.common.DeptOwnEnum;
import com.kaos.skynet.api.enums.common.MinFeeEnum;
import com.kaos.skynet.api.enums.common.PayKindEnum;
import com.kaos.skynet.api.enums.common.TransTypeEnum;
import com.kaos.skynet.api.enums.inpatient.fee.balance.BalanceStateEnum;

public class FinIpbFeeInfo {
    /**
     * 处方号
     */
    public String recipeNo = null;

    /**
     * 最小费用编码
     */
    public MinFeeEnum feeCode = null;

    /**
     * 执行科室编码 {@link FinIpbFeeInfo.AssociateEntity#executeDept}
     */
    public String executeDeptCode = null;

    /**
     * 结算序号
     */
    public Integer balanceNo = null;

    /**
     * 交易类型, 1正交易, 2反交易
     */
    public TransTypeEnum transType = null;

    /**
     * 住院流水号 {@link FinIpbFeeInfo.AssociateEntity#inMainInfo}
     */
    public String inpatientNo = null;

    /**
     * 姓名
     */
    public String name = null;

    /**
     * 结算类别
     */
    public PayKindEnum payKind = null;

    /**
     * 合同单位
     */
    public String pactCode = null;

    /**
     * 在院科室代码 {@link FinIpbFeeInfo.AssociateEntity#inHosDept}
     */
    public String inHosDeptCode = null;

    /**
     * 护士站代码 {@link FinIpbFeeInfo.AssociateEntity#nurseCell}
     */
    public String nurseCellCode = null;

    /**
     * 开立科室代码 {@link FinIpbFeeInfo.AssociateEntity#recipeDept}
     */
    public String recipeDeptCode = null;

    /**
     * 扣库科室代码 {@link FinIpbFeeInfo.AssociateEntity#stockDept}
     */
    public String stockDeptCode = null;

    /**
     * 开立医师代码 {@link FinIpbFeeInfo.AssociateEntity#recipeDoc}
     */
    public String recipeDocCode = null;

    /**
     * 费用金额
     */
    public Double totCost = null;

    /**
     * 自费金额
     */
    public Double ownCost = null;

    /**
     * 自付金额
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
     * 划价人 {@link FinIpbFeeInfo.AssociateEntity#chargeOperEmpl}
     */
    public String chargeOperCode = null;

    /**
     * 划价日期
     */
    public Date chargeDate = null;

    /**
     * 计费人 {@link FinIpbFeeInfo.AssociateEntity#feeOperEmpl}
     */
    public String feeOperCode = null;

    /**
     * 计费日期
     */
    public Date feeDate = null;

    /**
     * 结算人代码 {@link FinIpbFeeInfo.AssociateEntity#balanceOperEmpl}
     */
    public String balanceOperCode = null;

    /**
     * 结算时间
     */
    public Date balanceDate = null;

    /**
     * 结算发票号
     */
    public String invoiceNo = null;

    /**
     * 结算标志 0:未结算；1:已结算 2:已结转
     */
    public BalanceStateEnum balanceState = null;

    /**
     * 审核序号（襄阳项目用于费用发生时候患者管床医生）
     */
    public String checkNo = null;

    /**
    *
    */
    public Boolean babyFlag = null;

    /**
     * 扩展标志(公费患者是否使用了自费的项目0否,1是)
     */
    public Boolean extFlag = null;

    /**
     * 扩展代码(襄阳项目用于费用发生时候患者所在科室)
     */
    public String extCode = null;

    /**
     * 扩展日期
     */
    public Date extDate = null;

    /**
     * 扩展操作员 {@link FinIpbFeeInfo.AssociateEntity#extOperEmpl}
     */
    public String extOperCode = null;

    /**
     * 收费员科室 {@link FinIpbFeeInfo.AssociateEntity#feeOperDeptEmpl}
     */
    public String feeOperDeptCode = null;

    /**
     * 扩展标志1
     */
    public String extFlag1 = null;

    /**
     * 扩展标志2(收费方式0住院处直接收费,1护士站医嘱收费,2确认收费,3身份变更,4比例调整)
     */
    public String extFlag2 = null;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    public DeptOwnEnum deptOwn = null;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    public DeptOwnEnum employeeOwn = null;

    /**
     * 图特物质申请ID
     */
    public String wzApplyNo = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 执行科室 {@link FinIpbFeeInfo#executeDeptCode}
         */
        public DawnOrgDept executeDept = null;

        /**
         * 执行科室 {@link FinIpbFeeInfo#inpatientNo}
         */
        public FinIprInMainInfo inMainInfo = null;

        /**
         * 执行科室 {@link FinIpbFeeInfo#inHosDeptCode}
         */
        public DawnOrgDept inHosDept = null;

        /**
         * 执行科室 {@link FinIpbFeeInfo#nurseCellCode}
         */
        public DawnOrgDept nurseCell = null;

        /**
         * 执行科室 {@link FinIpbFeeInfo#recipeDeptCode}
         */
        public DawnOrgDept recipeDept = null;

        /**
         * 执行科室 {@link FinIpbFeeInfo#stockDeptCode}
         */
        public DawnOrgDept stockDept = null;

        /**
         * 执行科室 {@link FinIpbFeeInfo#recipeDocCode}
         */
        public DawnOrgEmpl recipeDoc = null;

        /**
         * 执行科室 {@link FinIpbFeeInfo#chargeOperCode}
         */
        public DawnOrgEmpl chargeOperEmpl = null;

        /**
         * 执行科室 {@link FinIpbFeeInfo#feeOperCode}
         */
        public DawnOrgEmpl feeOperEmpl = null;

        /**
         * 执行科室 {@link FinIpbFeeInfo#balanceOperCode}
         */
        public DawnOrgEmpl balanceOperEmpl = null;

        /**
         * 执行科室 {@link FinIpbFeeInfo#extOperCode}
         */
        public DawnOrgEmpl extOperEmpl = null;

        /**
         * 执行科室 {@link FinIpbFeeInfo#feeOperDeptCode}
         */
        public DawnOrgDept feeOperDeptEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
