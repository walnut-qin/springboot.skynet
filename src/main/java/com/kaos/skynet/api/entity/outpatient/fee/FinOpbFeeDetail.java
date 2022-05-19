package com.kaos.skynet.api.entity.outpatient.fee;

import java.util.Date;

import com.kaos.skynet.api.entity.common.DawnOrgDept;
import com.kaos.skynet.api.entity.common.DawnOrgEmpl;
import com.kaos.skynet.api.entity.common.fee.FinComFeeCodeStat;
import com.kaos.skynet.api.entity.outpatient.FinOprRegister;
import com.kaos.skynet.enums.common.DeptOwnEnum;
import com.kaos.skynet.enums.common.ItemGradeEnum;
import com.kaos.skynet.enums.common.MinFeeEnum;
import com.kaos.skynet.enums.common.SysClassEnum;
import com.kaos.skynet.enums.common.TransTypeEnum;
import com.kaos.skynet.enums.outpatient.fee.FeeDetailCancelFlagEnum;
import com.kaos.skynet.enums.outpatient.fee.FeeDetailCostSourceEnum;
import com.kaos.skynet.enums.outpatient.fee.FeeDetailPayFlagEnum;
import com.kaos.skynet.enums.outpatient.fee.FeeDetailSendFlagEnum;
import com.kaos.skynet.enums.pharmacy.DrugQualityEnum;

/**
 * 门诊费用明细（XYHIS.FIN_OPB_FEEDETAIL）
 */
public class FinOpbFeeDetail {
    /**
     * 处方单号
     */
    public String recipeNo = null;

    /**
     * 处方内项目流水号
     */
    public Integer seqNo = null;

    /**
     * 业务类型
     */
    public TransTypeEnum transType = null;

    /**
     * 门诊号 {@link FinOpbFeeDetail.AssociateEntity#register}
     */
    public String clinicCode = null;

    /**
     * 就诊卡号
     */
    public String cardNo = null;

    /**
     * 挂号日期
     */
    public Date regDate = null;

    /**
     * 挂号科室
     */
    public String regDeptCode = null;

    /**
     * 开方医师 {@link FinOpbFeeDetail.AssociateEntity#doctor}
     */
    public String doctCode = null;

    /**
     * 开方医师所在科室
     */
    public String docDeptCode = null;

    /**
     * 项目编码
     */
    public String itemCode = null;

    /**
     * 项目名称
     */
    public String itemName = null;

    /**
     * 是否为药品
     */
    public Boolean drugFlag = null;

    /**
     * 规格
     */
    public String specs = null;

    /**
     * 自制药标识
     */
    public Boolean selfMade = null;

    /**
     * 药品性质
     */
    public DrugQualityEnum drugQuality = null;

    /**
     * 剂型
     */
    public String doseModelCode = null;

    /**
     * 最小费用编码 {@link FinOpbFeeDetail.AssociateEntity#feeCodeStat}
     */
    public MinFeeEnum feeCode = null;

    /**
     * 系统类别
     */
    public SysClassEnum classCode = null;

    /**
     * 单价
     */
    public Double unitPrice = null;

    /**
     * 数量
     */
    public Double qty = null;

    /**
     * 草药的付数，其他药品为1
     */
    public Integer days = null;

    /**
     * 频次代码
     */
    public String frequencyCode = null;

    /**
     * 用法代码
     */
    public String usageCode = null;

    /**
     * 用法名称
     */
    public String useName = null;

    /**
     * 院内注射次数
     */
    public Integer injectNumber = null;

    /**
     * 加急标志
     */
    public Boolean emergencyFlag = null;

    /**
     * 样本类型
     */
    public String labType = null;

    /**
     * 检体
     */
    public String checkBody = null;

    /**
     * 每次用量
     */
    public Double doseOnce = null;

    /**
     * 每次用量单位
     */
    public String doseUnit = null;

    /**
     * 基本剂量
     */
    public Double baseDose = null;

    /**
     * 包装数量
     */
    public Integer packQty = null;

    /**
     * 计价单位
     */
    public String priceUnit = null;

    /**
     * 统筹金额
     */
    public Double pubCost = null;

    /**
     * 自付金额
     */
    public Double payCost = null;

    /**
     * 现金金额
     */
    public Double ownCost = null;

    /**
     * 执行科室编码
     */
    public String execDeptCode = null;

    /**
     * 执行科室名称
     */
    public String execDeptName = null;

    /**
     * 医保中心项目代码
     */
    public String centerCode = null;

    /**
     * 项目等级
     */
    public ItemGradeEnum itemGrade = null;

    /**
     * 主药标识
     */
    public Boolean mainDrugFlag = null;

    /**
     * 组合号
     */
    public String combNo = null;

    /**
     * 划价人编码
     */
    public String operCode = null;

    /**
     * 划价时间
     */
    public Date operDate = null;

    /**
     * 类型
     */
    public FeeDetailPayFlagEnum payFlag = null;

    /**
     * 取消标识
     */
    public FeeDetailCancelFlagEnum cancelFlag = null;

    /**
     * 收费人
     */
    public String feeEmplCode = null;

    /**
     * 收费日期
     */
    public Date feeDate = null;

    /**
     * 发票号
     */
    public String invoiceNo = null;

    /**
     * 发票科目代码
     */
    public String invoCode = null;

    /**
     * 发票内流水号
     */
    public String invoSequence = null;

    /**
     * 确认标识
     */
    public Boolean confirmFlag = null;

    /**
     * 确认人
     */
    public String confirmEmplCode = null;

    /**
     * 确认科室编码
     */
    public String confirmDeptCode = null;

    /**
     * 确认时间
     */
    public Date confirmDate = null;

    /**
     * 优惠金额
     */
    public Double ecoCost = null;

    /**
     * 发票序号，一次结算产生多张发票的combNo
     */
    public String invoiceSeq = null;

    /**
     * 新项目比例
     */
    public Double newItemRate = null;

    /**
     * 原项目比例
     */
    public Double oldItemRate = null;

    /**
     * 特殊项目标志
     */
    public Boolean extFlag = null;

    /**
     * 正常个人体检
     */
    public Boolean extFlag1 = null;

    /**
     * 日结
     */
    public Boolean extFlag2 = null;

    /**
     * 包装单位
     */
    public Boolean extFlag3 = null;

    /**
     * 复合项目代码
     */
    public String packageCode = null;

    /**
     * 复合项目名称
     */
    public String packageName = null;

    /**
     * 可退数量
     */
    public Double noBackNum = null;

    /**
     * 确认数量
     */
    public Double confirmNum = null;

    /**
     * 已确认住院次数
     */
    public Integer confirmInject = null;

    /**
     * 医嘱项目流水号
     */
    public String moOrder = null;

    /**
     * 条码号
     */
    public String sampleId = null;

    /**
     * 收费序列
     */
    public String recipeSeq = null;

    /**
     * 药品超标金额
     */
    public Double excessCost = null;

    /**
     * 自费药金额
     */
    public Double drugOwnCost = null;

    /**
     * 费用来源
     */
    public FeeDetailCostSourceEnum costSource = null;

    /**
     * 附材标识
     */
    public Boolean subJobFlag = null;

    /**
     * 已扣账户标识
     */
    public Boolean accountFlag = null;

    /**
     * 更新库存的流水号
     */
    public Integer updateSequenceNo = null;

    /**
     * 医生所属科室
     */
    public String docInDept = null;

    /**
     * 医疗组编码
     */
    public String medGrpCode = null;

    /**
     * 执行单打印标识
     */
    public Boolean isPrintExeBill = null;

    /**
     * 门诊输液卡打印标识
     */
    public Boolean isPrintDrugCard = null;

    /**
     * 门诊一卡通号
     */
    public String accountNo = null;

    /**
     * 药品批次号
     */
    public String grpCode = null;

    /**
     * 院区标识
     */
    public DeptOwnEnum deptOwn = null;

    /**
     * 职员院区标识
     */
    public DeptOwnEnum emplOwn = null;

    /**
     * 蓝卫lis已打条码标记 0未打 1已打
     */
    public Boolean lisFlag = null;

    /**
     * 东软lis已打条码标记 0未打 1已打
     */
    public Boolean drLisFlag = null;

    /**
     * 绿通标识
     */
    public Boolean greeRoad = null;

    /**
     * 转费用该表示
     */
    public FeeDetailSendFlagEnum sendFlag = null;

    /**
     * 转费操作员
     */
    public String sendOperCode = null;

    /**
     * 转费时间
     */
    public Date sendDate = null;

    /**
     * 转费备注
     */
    public String sendRemark = null;

    /**
     * 互联网核酸开立、检查开立的非药品术语号
     */
    public String aTermNo = null;

    /**
     * 襄阳预约状态(用于PACS预约平台) false: 未预约 true: 已预约
     */
    public Boolean orderState = null;

    /**
     * 条形码
     */
    public String barCode = null;

    /**
     * 材料编码
     */
    public String invCode = null;

    /**
     * 超标金额
     */
    public Double overCost = null;

    /**
     * 日间医嘱，用于分组(1, 2)
     */
    public String prepType = null;

    /**
     * 自付比例（医保费用上传出参，用于计算甲乙类费用）
     */
    public Double selfPayProp = null;

    /**
     * 全自费金额（医保费用上传出参）
     */
    public Double fulAmtOwnPayAmt = null;

    /**
     * 医保费用代码类别
     */
    public String centerFeeCode = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 挂号信息 {@link FinOpbFeeDetail#clinicCode}
         */
        public FinOprRegister register = null;

        /**
         * 开单科室
         */
        public DawnOrgDept regDept = null;

        /**
         * 开方医师 {@link FinOpbFeeDetail#doctCode}
         */
        public DawnOrgEmpl doctor = null;

        /**
         * 统计大小类对照 {@link FinOpbFeeDetail#feeCode}
         */
        public FinComFeeCodeStat feeCodeStat = null;

        /**
         * 实体：执行科室
         */
        public DawnOrgDept execDept = null;

        /**
         * 实体：划价员
         */
        public DawnOrgEmpl operEmpl = null;

        /**
         * 收费员
         */
        public DawnOrgEmpl feeEmpl = null;

        /**
         * 确认人
         */
        public DawnOrgEmpl cfmEmpl = null;

        /**
         * 确认科室
         */
        public DawnOrgDept cfmDept = null;

        /**
         * 转费操作员
         */
        public DawnOrgEmpl sendEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
