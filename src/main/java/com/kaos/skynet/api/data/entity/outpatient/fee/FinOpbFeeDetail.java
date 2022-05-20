package com.kaos.skynet.api.data.entity.outpatient.fee;

import java.util.Date;

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

import lombok.Data;

/**
 * 门诊费用明细（XYHIS.FIN_OPB_FEEDETAIL）
 */
@Data
public class FinOpbFeeDetail {
    /**
     * 处方单号
     */
    private String recipeNo = null;

    /**
     * 处方内项目流水号
     */
    private Integer seqNo = null;

    /**
     * 业务类型
     */
    private TransTypeEnum transType = null;

    /**
     * 门诊号
     */
    private String clinicCode = null;

    /**
     * 就诊卡号
     */
    private String cardNo = null;

    /**
     * 挂号日期
     */
    private Date regDate = null;

    /**
     * 挂号科室
     */
    private String regDeptCode = null;

    /**
     * 开方医师
     */
    private String doctCode = null;

    /**
     * 开方医师所在科室
     */
    private String docDeptCode = null;

    /**
     * 项目编码
     */
    private String itemCode = null;

    /**
     * 项目名称
     */
    private String itemName = null;

    /**
     * 是否为药品
     */
    private Boolean drugFlag = null;

    /**
     * 规格
     */
    private String specs = null;

    /**
     * 自制药标识
     */
    private Boolean selfMade = null;

    /**
     * 药品性质
     */
    private DrugQualityEnum drugQuality = null;

    /**
     * 剂型
     */
    private String doseModelCode = null;

    /**
     * 最小费用编码
     */
    private MinFeeEnum feeCode = null;

    /**
     * 系统类别
     */
    private SysClassEnum classCode = null;

    /**
     * 单价
     */
    private Double unitPrice = null;

    /**
     * 数量
     */
    private Double qty = null;

    /**
     * 草药的付数，其他药品为1
     */
    private Integer days = null;

    /**
     * 频次代码
     */
    private String frequencyCode = null;

    /**
     * 用法代码
     */
    private String usageCode = null;

    /**
     * 用法名称
     */
    private String useName = null;

    /**
     * 院内注射次数
     */
    private Integer injectNumber = null;

    /**
     * 加急标志
     */
    private Boolean emergencyFlag = null;

    /**
     * 样本类型
     */
    private String labType = null;

    /**
     * 检体
     */
    private String checkBody = null;

    /**
     * 每次用量
     */
    private Double doseOnce = null;

    /**
     * 每次用量单位
     */
    private String doseUnit = null;

    /**
     * 基本剂量
     */
    private Double baseDose = null;

    /**
     * 包装数量
     */
    private Integer packQty = null;

    /**
     * 计价单位
     */
    private String priceUnit = null;

    /**
     * 统筹金额
     */
    private Double pubCost = null;

    /**
     * 自付金额
     */
    private Double payCost = null;

    /**
     * 现金金额
     */
    private Double ownCost = null;

    /**
     * 执行科室编码
     */
    private String execDeptCode = null;

    /**
     * 执行科室名称
     */
    private String execDeptName = null;

    /**
     * 医保中心项目代码
     */
    private String centerCode = null;

    /**
     * 项目等级
     */
    private ItemGradeEnum itemGrade = null;

    /**
     * 主药标识
     */
    private Boolean mainDrugFlag = null;

    /**
     * 组合号
     */
    private String combNo = null;

    /**
     * 划价人编码
     */
    private String operCode = null;

    /**
     * 划价时间
     */
    private Date operDate = null;

    /**
     * 类型
     */
    private FeeDetailPayFlagEnum payFlag = null;

    /**
     * 取消标识
     */
    private FeeDetailCancelFlagEnum cancelFlag = null;

    /**
     * 收费人
     */
    private String feeEmplCode = null;

    /**
     * 收费日期
     */
    private Date feeDate = null;

    /**
     * 发票号
     */
    private String invoiceNo = null;

    /**
     * 发票科目代码
     */
    private String invoCode = null;

    /**
     * 发票内流水号
     */
    private String invoSequence = null;

    /**
     * 确认标识
     */
    private Boolean confirmFlag = null;

    /**
     * 确认人
     */
    private String confirmEmplCode = null;

    /**
     * 确认科室编码
     */
    private String confirmDeptCode = null;

    /**
     * 确认时间
     */
    private Date confirmDate = null;

    /**
     * 优惠金额
     */
    private Double ecoCost = null;

    /**
     * 发票序号，一次结算产生多张发票的combNo
     */
    private String invoiceSeq = null;

    /**
     * 新项目比例
     */
    private Double newItemRate = null;

    /**
     * 原项目比例
     */
    private Double oldItemRate = null;

    /**
     * 特殊项目标志
     */
    private Boolean extFlag = null;

    /**
     * 正常个人体检
     */
    private Boolean extFlag1 = null;

    /**
     * 日结
     */
    private Boolean extFlag2 = null;

    /**
     * 包装单位
     */
    private Boolean extFlag3 = null;

    /**
     * 复合项目代码
     */
    private String packageCode = null;

    /**
     * 复合项目名称
     */
    private String packageName = null;

    /**
     * 可退数量
     */
    private Double noBackNum = null;

    /**
     * 确认数量
     */
    private Double confirmNum = null;

    /**
     * 已确认住院次数
     */
    private Integer confirmInject = null;

    /**
     * 医嘱项目流水号
     */
    private String moOrder = null;

    /**
     * 条码号
     */
    private String sampleId = null;

    /**
     * 收费序列
     */
    private String recipeSeq = null;

    /**
     * 药品超标金额
     */
    private Double excessCost = null;

    /**
     * 自费药金额
     */
    private Double drugOwnCost = null;

    /**
     * 费用来源
     */
    private FeeDetailCostSourceEnum costSource = null;

    /**
     * 附材标识
     */
    private Boolean subJobFlag = null;

    /**
     * 已扣账户标识
     */
    private Boolean accountFlag = null;

    /**
     * 更新库存的流水号
     */
    private Integer updateSequenceNo = null;

    /**
     * 医生所属科室
     */
    private String docInDept = null;

    /**
     * 医疗组编码
     */
    private String medGrpCode = null;

    /**
     * 执行单打印标识
     */
    private Boolean isPrintExeBill = null;

    /**
     * 门诊输液卡打印标识
     */
    private Boolean isPrintDrugCard = null;

    /**
     * 门诊一卡通号
     */
    private String accountNo = null;

    /**
     * 药品批次号
     */
    private String grpCode = null;

    /**
     * 院区标识
     */
    private DeptOwnEnum deptOwn = null;

    /**
     * 职员院区标识
     */
    private DeptOwnEnum emplOwn = null;

    /**
     * 蓝卫lis已打条码标记 0未打 1已打
     */
    private Boolean lisFlag = null;

    /**
     * 东软lis已打条码标记 0未打 1已打
     */
    private Boolean drLisFlag = null;

    /**
     * 绿通标识
     */
    private Boolean greeRoad = null;

    /**
     * 转费用该表示
     */
    private FeeDetailSendFlagEnum sendFlag = null;

    /**
     * 转费操作员
     */
    private String sendOperCode = null;

    /**
     * 转费时间
     */
    private Date sendDate = null;

    /**
     * 转费备注
     */
    private String sendRemark = null;

    /**
     * 互联网核酸开立、检查开立的非药品术语号
     */
    private String aTermNo = null;

    /**
     * 襄阳预约状态(用于PACS预约平台) false: 未预约 true: 已预约
     */
    private Boolean orderState = null;

    /**
     * 条形码
     */
    private String barCode = null;

    /**
     * 材料编码
     */
    private String invCode = null;

    /**
     * 超标金额
     */
    private Double overCost = null;

    /**
     * 日间医嘱，用于分组(1, 2)
     */
    private String prepType = null;

    /**
     * 自付比例（医保费用上传出参，用于计算甲乙类费用）
     */
    private Double selfPayProp = null;

    /**
     * 全自费金额（医保费用上传出参）
     */
    private Double fulAmtOwnPayAmt = null;

    /**
     * 医保费用代码类别
     */
    private String centerFeeCode = null;
}
