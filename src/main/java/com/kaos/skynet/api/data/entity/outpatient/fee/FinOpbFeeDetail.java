package com.kaos.skynet.api.data.entity.outpatient.fee;

import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.MinFeeEnum;
import com.kaos.skynet.api.enums.common.ItemGradeEnum;
import com.kaos.skynet.api.enums.common.SysClassEnum;
import com.kaos.skynet.api.enums.common.TransTypeEnum;
import com.kaos.skynet.api.enums.outpatient.fee.FeeDetailCancelFlagEnum;
import com.kaos.skynet.api.enums.outpatient.fee.FeeDetailCostSourceEnum;
import com.kaos.skynet.api.enums.outpatient.fee.FeeDetailPayFlagEnum;
import com.kaos.skynet.api.enums.outpatient.fee.FeeDetailSendFlagEnum;
import com.kaos.skynet.api.enums.pharmacy.DrugQualityEnum;
import com.kaos.skynet.core.IntegerUtils;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Data;

/**
 * 门诊费用明细（XYHIS.FIN_OPB_FEEDETAIL）
 */
@Data
@Builder
public class FinOpbFeeDetail {
    /**
     * 处方单号
     */
    private String recipeNo;

    /**
     * 处方内项目流水号
     */
    private Integer seqNo;

    /**
     * 业务类型
     */
    private TransTypeEnum transType;

    /**
     * 门诊号
     */
    private String clinicCode;

    /**
     * 就诊卡号
     */
    private String cardNo;

    /**
     * 挂号日期
     */
    private Date regDate;

    /**
     * 挂号科室
     */
    private String regDeptCode;

    /**
     * 开方医师
     */
    private String doctCode;

    /**
     * 开方医师所在科室
     */
    private String docDeptCode;

    /**
     * 项目编码
     */
    private String itemCode;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 是否为药品
     */
    private Boolean drugFlag;

    /**
     * 规格
     */
    private String specs;

    /**
     * 自制药标识
     */
    private Boolean selfMade;

    /**
     * 药品性质
     */
    private DrugQualityEnum drugQuality;

    /**
     * 剂型
     */
    private String doseModelCode;

    /**
     * 最小费用编码
     */
    private MinFeeEnum feeCode;

    /**
     * 系统类别
     */
    private SysClassEnum classCode;

    /**
     * 单价
     */
    private Double unitPrice;

    /**
     * 数量
     */
    private Double qty;

    /**
     * 草药的付数，其他药品为1
     */
    private Integer days;

    /**
     * 频次代码
     */
    private String frequencyCode;

    /**
     * 用法代码
     */
    private String usageCode;

    /**
     * 用法名称
     */
    private String useName;

    /**
     * 院内注射次数
     */
    private Integer injectNumber;

    /**
     * 加急标志
     */
    private Boolean emergencyFlag;

    /**
     * 样本类型
     */
    private String labType;

    /**
     * 检体
     */
    private String checkBody;

    /**
     * 每次用量
     */
    private Double doseOnce;

    /**
     * 每次用量单位
     */
    private String doseUnit;

    /**
     * 基本剂量
     */
    private Double baseDose;

    /**
     * 包装数量
     */
    private Integer packQty;

    /**
     * 计价单位
     */
    private String priceUnit;

    /**
     * 统筹金额
     */
    private Double pubCost;

    /**
     * 自付金额
     */
    private Double payCost;

    /**
     * 现金金额
     */
    private Double ownCost;

    /**
     * 执行科室编码
     */
    private String execDeptCode;

    /**
     * 执行科室名称
     */
    private String execDeptName;

    /**
     * 医保中心项目代码
     */
    private String centerCode;

    /**
     * 项目等级
     */
    private ItemGradeEnum itemGrade;

    /**
     * 主药标识
     */
    private Boolean mainDrugFlag;

    /**
     * 组合号
     */
    private String combNo;

    /**
     * 划价人编码
     */
    private String operCode;

    /**
     * 划价时间
     */
    private Date operDate;

    /**
     * 类型
     */
    private FeeDetailPayFlagEnum payFlag;

    /**
     * 取消标识
     */
    private FeeDetailCancelFlagEnum cancelFlag;

    /**
     * 收费人
     */
    private String feeEmplCode;

    /**
     * 收费日期
     */
    private Date feeDate;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 发票科目代码
     */
    private String invoCode;

    /**
     * 发票内流水号
     */
    private String invoSequence;

    /**
     * 确认标识
     */
    private Boolean confirmFlag;

    /**
     * 确认人
     */
    private String confirmEmplCode;

    /**
     * 确认科室编码
     */
    private String confirmDeptCode;

    /**
     * 确认时间
     */
    private Date confirmDate;

    /**
     * 优惠金额
     */
    private Double ecoCost;

    /**
     * 发票序号，一次结算产生多张发票的combNo
     */
    private String invoiceSeq;

    /**
     * 新项目比例
     */
    private Double newItemRate;

    /**
     * 原项目比例
     */
    private Double oldItemRate;

    /**
     * 特殊项目标志
     */
    private Boolean extFlag;

    /**
     * 正常个人体检
     */
    private Boolean extFlag1;

    /**
     * 日结
     */
    private Boolean extFlag2;

    /**
     * 包装单位
     */
    private Boolean extFlag3;

    /**
     * 复合项目代码
     */
    private String packageCode;

    /**
     * 复合项目名称
     */
    private String packageName;

    /**
     * 可退数量
     */
    private Double noBackNum;

    /**
     * 确认数量
     */
    private Double confirmNum;

    /**
     * 已确认住院次数
     */
    private Integer confirmInject;

    /**
     * 医嘱项目流水号
     */
    private String moOrder;

    /**
     * 条码号
     */
    private String sampleId;

    /**
     * 收费序列
     */
    private String recipeSeq;

    /**
     * 药品超标金额
     */
    private Double excessCost;

    /**
     * 自费药金额
     */
    private Double drugOwnCost;

    /**
     * 费用来源
     */
    private FeeDetailCostSourceEnum costSource;

    /**
     * 附材标识
     */
    private Boolean subJobFlag;

    /**
     * 已扣账户标识
     */
    private Boolean accountFlag;

    /**
     * 更新库存的流水号
     */
    private Integer updateSequenceNo;

    /**
     * 医生所属科室
     */
    private String docInDept;

    /**
     * 医疗组编码
     */
    private String medGrpCode;

    /**
     * 执行单打印标识
     */
    private Boolean isPrintExeBill;

    /**
     * 门诊输液卡打印标识
     */
    private Boolean isPrintDrugCard;

    /**
     * 门诊一卡通号
     */
    private String accountNo;

    /**
     * 药品批次号
     */
    private String grpCode;

    /**
     * 院区标识
     */
    private DeptOwnEnum deptOwn;

    /**
     * 职员院区标识
     */
    private DeptOwnEnum emplOwn;

    /**
     * 蓝卫lis已打条码标记 0未打 1已打
     */
    private Boolean lisFlag;

    /**
     * 东软lis已打条码标记 0未打 1已打
     */
    private Boolean drLisFlag;

    /**
     * 绿通标识
     */
    private Boolean greeRoad;

    /**
     * 转费用该表示
     */
    private FeeDetailSendFlagEnum sendFlag;

    /**
     * 转费操作员
     */
    private String sendOperCode;

    /**
     * 转费时间
     */
    private Date sendDate;

    /**
     * 转费备注
     */
    private String sendRemark;

    /**
     * 互联网核酸开立、检查开立的非药品术语号
     */
    private String aTermNo;

    /**
     * 襄阳预约状态(用于PACS预约平台) false: 未预约 true: 已预约
     */
    private Boolean orderState;

    /**
     * 条形码
     */
    private String barCode;

    /**
     * 材料编码
     */
    private String invCode;

    /**
     * 超标金额
     */
    private Double overCost;

    /**
     * 日间医嘱，用于分组(1, 2)
     */
    private String prepType;

    /**
     * 自付比例（医保费用上传出参，用于计算甲乙类费用）
     */
    private Double selfPayProp;

    /**
     * 全自费金额（医保费用上传出参）
     */
    private Double fulAmtOwnPayAmt;

    /**
     * 医保费用代码类别
     */
    private String centerFeeCode;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinOpbFeeDetail) {
            var that = (FinOpbFeeDetail) arg0;
            return StringUtils.equals(this.recipeNo, that.recipeNo)
                    && IntegerUtils.equals(this.seqNo, that.seqNo)
                    && this.transType == that.transType;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(recipeNo, seqNo, transType);
    }
}
