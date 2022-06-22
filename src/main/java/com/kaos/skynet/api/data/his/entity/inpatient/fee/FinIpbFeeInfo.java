package com.kaos.skynet.api.data.his.entity.inpatient.fee;

import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.his.entity.inpatient.fee.balance.FinIpbBalanceHead.BalanceStateEnum;
import com.kaos.skynet.api.data.his.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.his.enums.MinFeeEnum;
import com.kaos.skynet.api.data.his.enums.PayKindEnum;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;
import com.kaos.skynet.core.util.IntegerUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FinIpbFeeInfo {
    /**
     * 处方号
     */
    private String recipeNo;

    /**
     * 最小费用编码
     */
    private MinFeeEnum feeCode;

    /**
     * 执行科室编码
     */
    private String executeDeptCode;

    /**
     * 结算序号
     */
    private Integer balanceNo;

    /**
     * 交易类型, 1正交易, 2反交易
     */
    private TransTypeEnum transType;

    /**
     * 住院流水号 {@link FinIpbFeeInfo.AssociateEntity#inMainInfo}
     */
    private String inpatientNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 结算类别
     */
    private PayKindEnum payKind;

    /**
     * 合同单位
     */
    private String pactCode;

    /**
     * 在院科室代码 {@link FinIpbFeeInfo.AssociateEntity#inHosDept}
     */
    private String inHosDeptCode;

    /**
     * 护士站代码 {@link FinIpbFeeInfo.AssociateEntity#nurseCell}
     */
    private String nurseCellCode;

    /**
     * 开立科室代码 {@link FinIpbFeeInfo.AssociateEntity#recipeDept}
     */
    private String recipeDeptCode;

    /**
     * 扣库科室代码 {@link FinIpbFeeInfo.AssociateEntity#stockDept}
     */
    private String stockDeptCode;

    /**
     * 开立医师代码 {@link FinIpbFeeInfo.AssociateEntity#recipeDoc}
     */
    private String recipeDocCode;

    /**
     * 费用金额
     */
    private Double totCost;

    /**
     * 自费金额
     */
    private Double ownCost;

    /**
     * 自付金额
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
     * 划价人 {@link FinIpbFeeInfo.AssociateEntity#chargeOperEmpl}
     */
    private String chargeOperCode;

    /**
     * 划价日期
     */
    private Date chargeDate;

    /**
     * 计费人 {@link FinIpbFeeInfo.AssociateEntity#feeOperEmpl}
     */
    private String feeOperCode;

    /**
     * 计费日期
     */
    private Date feeDate;

    /**
     * 结算人代码 {@link FinIpbFeeInfo.AssociateEntity#balanceOperEmpl}
     */
    private String balanceOperCode;

    /**
     * 结算时间
     */
    private Date balanceDate;

    /**
     * 结算发票号
     */
    private String invoiceNo;

    /**
     * 结算标志 0:未结算；1:已结算 2:已结转
     */
    private BalanceStateEnum balanceState;

    /**
     * 审核序号（襄阳项目用于费用发生时候患者管床医生）
     */
    private String checkNo;

    /**
    *
    */
    private Boolean babyFlag;

    /**
     * 扩展标志(公费患者是否使用了自费的项目0否,1是)
     */
    private Boolean extFlag;

    /**
     * 扩展代码(襄阳项目用于费用发生时候患者所在科室)
     */
    private String extCode;

    /**
     * 扩展日期
     */
    private Date extDate;

    /**
     * 扩展操作员 {@link FinIpbFeeInfo.AssociateEntity#extOperEmpl}
     */
    private String extOperCode;

    /**
     * 收费员科室 {@link FinIpbFeeInfo.AssociateEntity#feeOperDeptEmpl}
     */
    private String feeOperDeptCode;

    /**
     * 扩展标志1
     */
    private String extFlag1;

    /**
     * 扩展标志2(收费方式0住院处直接收费,1护士站医嘱收费,2确认收费,3身份变更,4比例调整)
     */
    private String extFlag2;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    private DeptOwnEnum deptOwn;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    private DeptOwnEnum employeeOwn;

    /**
     * 图特物质申请ID
     */
    private String wzApplyNo;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIpbFeeInfo) {
            var that = (FinIpbFeeInfo) arg0;
            return StringUtils.equals(this.recipeNo, that.recipeNo)
                    && this.feeCode == that.feeCode
                    && StringUtils.equals(this.executeDeptCode, that.executeDeptCode)
                    && IntegerUtils.equals(this.balanceNo, that.balanceNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(recipeNo, feeCode, executeDeptCode, balanceNo);
    }
}
