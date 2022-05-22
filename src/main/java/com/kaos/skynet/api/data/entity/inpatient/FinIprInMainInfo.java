package com.kaos.skynet.api.data.entity.inpatient;

import java.time.LocalDateTime;

import com.kaos.skynet.api.data.enums.BloodTypeEnum;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.enums.common.PayKindEnum;
import com.kaos.skynet.api.enums.inpatient.InAvenueEnum;
import com.kaos.skynet.api.enums.inpatient.InCircsEnum;
import com.kaos.skynet.api.enums.inpatient.InSourceEnum;
import com.kaos.skynet.api.enums.inpatient.InStateEnum;

import lombok.Data;

/**
 * 住院主表信息(XYHIS.FIN_IPR_INMAININFO)
 */
@Data
public class FinIprInMainInfo {
    /**
     * 住院流水号
     */
    private String inpatientNo = null;

    /**
     *
     */
    private String medicalType = null;

    /**
     * 住院号
     */
    private String patientNo = null;

    /**
     * 就诊卡号
     */
    private String cardNo = null;

    /**
     * 社保卡号
     */
    private String medCardNo = null;

    /**
     * 姓名
     */
    private String name = null;

    /**
     * 性别
     */
    private SexEnum sex = null;

    /**
     * 身份证号
     */
    private String idenNo = null;

    /**
     * 拼写码
     */
    private String spellCode = null;

    /**
     * 生日
     */
    private LocalDateTime birthday = null;

    /**
     * 职业代码
     */
    private String profCode = null;

    /**
     * 工作单位
     */
    private String workName = null;

    /**
     * 工作电话
     */
    private String workTel = null;

    /**
     * 工作邮编
     */
    private String workZip = null;

    /**
     * 家庭住址
     */
    private String home = null;

    /**
     * 家庭电话
     */
    private String homeTel = null;

    /**
     * 家庭邮编
     */
    private String homeZip = null;

    /**
     * 籍贯
     */
    private String dist = null;

    /**
     * 出生地代码
     */
    private String birthArea = null;

    /**
     * 民族
     */
    private String nationCode = null;

    /**
     * 联系人姓名
     */
    private String linkmanName = null;

    /**
     * 联系人电话
     */
    private String linkmanTel = null;

    /**
     * 联系人地址
     */
    private String linkmanAddress = null;

    /**
     * 联系人关系
     */
    private String linkmanRelation = null;

    /**
     * 婚姻状况
     */
    private String mari = null;

    /**
     * 国籍
     */
    private String country = null;

    /**
     * 身高
     */
    private Double height = null;

    /**
     * 体重
     */
    private Double weight = null;

    /**
     * 血压
     */
    private String bloodDress = null;

    /**
     * 血型
     */
    private BloodTypeEnum bloodCode = null;

    /**
     * 重大疾病编码
     */
    private Boolean hepatitisFlag = null;

    /**
     * 过敏标志
     */
    private Boolean anaphyFlag = null;

    /**
     * 入院日期
     */
    private LocalDateTime inDate = null;

    /**
     * 科室代码
     */
    private String deptCode = null;

    /**
     * 科室名称
     */
    private String deptName = null;

    /**
     * 结算类型
     */
    private PayKindEnum payKind = null;

    /**
     * 合同单位编码
     */
    private String pactCode = null;

    /**
     * 合同单位名
     */
    private String pactName = null;

    /**
     * 床位号
     */
    private String bedNo = null;

    /**
     * 病区编码
     */
    private String nurseCellCode = null;

    /**
     * 病区名称
     */
    private String nurseCellName = null;

    /**
     * 住院医师代码
     */
    private String houseDocCode = null;

    /**
     * 住院医师姓名
     */
    private String houseDocName = null;

    /**
     * 主治医师代码
     */
    private String chargeDocCode = null;

    /**
     * 主治医师姓名
     */
    private String chargeDocName = null;

    /**
     * 主任医师代码
     */
    private String chiefDocCode = null;

    /**
     * 主任医师姓名
     */
    private String chiefDocName = null;

    /**
     * 责任护士编码
     */
    private String dutyNurseCode = null;

    /**
     * 责任护士姓名
     */
    private String dutyNurseName = null;

    /**
     * 入院情况
     */
    private InCircsEnum inCircs = null;

    /**
     * 入院途径
     */
    private InAvenueEnum inAvenue = null;

    /**
     * 入院来源
     */
    private InSourceEnum inSourceEnum = null;

    /**
     * 入院次数
     */
    private Integer inTimes = null;

    /**
     * 预交金额(未结)
     */
    private Double prepayCost = null;

    /**
     * 转入预交金额（未结)
     */
    private Double changePrepayCost = null;

    /**
     * 警戒线
     */
    private Double moneyAlert = null;

    /**
     * 总费用
     */
    private Double totCost = null;

    /**
     * 自费金额
     */
    private Double ownCost = null;

    /**
     * 自付金额(未结)
     */
    private Double payCost = null;

    /**
     * 统筹金额(未结)
     */
    private Double pubCost = null;

    /**
     * 优惠金额(未结)
     */
    private Double ecoCost = null;

    /**
     * 余额(未结)
     */
    private Double feeCost = null;

    /**
     * 转入费用金额(未结)
     */
    private Double changeTotCost = null;

    /**
     * 待遇上限
     */
    private Double upperLimit = null;

    /**
     * 固定费用间隔天数
     */
    private Integer feeInterval = null;

    /**
     * 结算序号
     */
    private Integer balanceNo = null;

    /**
     * 费用金额(已结)
     */
    private Double balanceCost = null;

    /**
     * 预交金额(已结)
     */
    private Double balancePrepay = null;

    /**
     * 结算日期
     */
    private LocalDateTime balanceDate = null;

    /**
     * 是否关账
     */
    private Boolean stopAccount = null;

    /**
     * 是否为婴儿
     */
    private Boolean babyFlag = null;

    /**
     * 病案状态: 0 无需病案 1 需要病案 2 医生站形成病案 3 病案室形成病案 4病案封存
     */
    private String caseFlag = null;

    /**
     * 在院状态
     */
    private InStateEnum inState = null;

    /**
     * 是否请假
     */
    private Boolean leaveFlag = null;

    /**
     * 出院日期(预约)
     */
    private LocalDateTime prepayOutDate = null;

    /**
     *
     */
    private LocalDateTime outDate = null;

    /**
     * 转归代号
     */
    private String zg = null;

    /**
     * 开据医师
     */
    private String emplCode = null;

    /**
     * 是否在ICU
     */
    private Boolean inICU = null;

    /**
     * 病案送入病案室否0未1送
     */
    private Boolean caseSendFlag = null;

    /**
     * 护理级别(TEND):名称显示护理级别名称(一级护理，二级护理，三级护理)
     */
    private String tend = null;

    /**
     * 病危：0 普通 1 病重 2 病危
     */
    private String criticalFlag = null;

    /**
     * 上次固定费用时间
     */
    private LocalDateTime prefixFeeDate = null;

    /**
     * 操作员
     */
    private String operCode = null;

    /**
     *
     */
    private LocalDateTime operDate = null;

    /**
     * 血滞纳金
     */
    private Double bloodLateFee = null;

    /**
     * 公费患者日限额
     */
    private Double dayLimit = null;

    /**
     * 公费患者日限额累计
     */
    private Double limitTot = null;

    /**
     * 公费患者日限额超标部分金额
     */
    private Double limitOverTop = null;

    /**
     * 门诊诊断
     */
    private String clinicDiagnose = null;

    /**
     * 生育保险患者电脑号(医保用作欠费标记1为欠费)
     */
    private String procreatePcNo = null;

    /**
     * 饮食
     */
    private String dieteticMark = null;

    /**
     * 公费患者公费药品累计(日限额)
     */
    private Double bursaryTotMedFee = null;

    /**
     * 备注
     */
    private String memo = null;

    /**
     * 床位上限
     */
    private Double bedLimit = null;

    /**
     * 空调上限
     */
    private Double airLimit = null;

    /**
     * 床费超标处理 0超标不限 1超标自理 2超标不计
     */
    private String bedOverDeal = null;

    /**
     * 扩展标记[无费入院标记]
     */
    private String extFlag = null;

    /**
     * 扩展标记1
     */
    private String extFlag1 = null;

    /**
     * 扩展标记2(襄阳保存特殊自费类型:火灾，弃婴，车祸等)
     */
    private String extFlag2 = null;

    /**
     * 膳食花费总额
     */
    private Double boardCost = null;

    /**
     * 膳食预交金额
     */
    private Double boardPrepay = null;

    /**
     * 膳食结算状态：0在院 1出院
     */
    private String boardState = null;

    /**
     * 自费比例
     */
    private Double ownRate = null;

    /**
     * 自付比例
     */
    private Double payRate = null;

    /**
     * 扩展数值（中山一用作－剩余统筹金额）
     */
    private Double extNumber = null;

    /**
     * 扩展编码（襄阳保存医保特殊记录：15r重复住院等）
     */
    private String extCode = null;

    /**
     * 诊断名称（建议用此保存主诊断）
     */
    private String diagName = null;

    /**
     * 是否加密
     */
    private Boolean isEncryptName = null;

    /**
     * 密文
     */
    private String normalName = null;

    /**
     * 证件类型
     */
    private String idCardType = null;

    /**
     * M 金额 D时间段
     */
    private String alterType = null;

    /**
     * 警戒线开始时间
     */
    private LocalDateTime alterBegin = null;

    /**
     * 警戒线结束时间
     */
    private LocalDateTime alterEnd = null;

    /**
     * 警戒线批准人
     */
    private String alterApproveCode = null;

    /**
     * 警戒线批准原因
     */
    private LocalDateTime alterApproveDate = null;

    /**
     * 人民医院旧系统资格证书编号(导数据用)[襄阳暂存质控护士编码]
     */
    private String oldSiNo = null;

    /**
     * 患者医保审核确认标记
     */
    private String localExtFlag1 = null;

    /**
     * 医保普通备注
     */
    private String localExtFlag2 = null;

    /**
     * EMR住院流水号 SEQ_FIN_INPATIENTID
     */
    private String emrInpatientId = null;

    /**
     * 是否是单病种患者0：非单病种1：单病种
     */
    private Boolean isSingleDis = null;

    /**
     * 单病种ICD编码
     */
    private String singleDisId = null;

    /**
     * 定额结算手术名称
     */
    private String diseaseName = null;

    /**
     * 定额结算手术定额
     */
    private Double diseaseCost = null;

    /**
     * 材料费用
     */
    private String materialCost = null;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    private DeptOwnEnum deptOwn = null;

    /**
     * 急诊绿色通道结账
     */
    private Boolean isGreenBalance = null;

    /**
     * 跨省医保省编码
     */
    private String siProvinceCode = null;

    /**
     * 跨省医保省名称
     */
    private String siProvinceName = null;

    /**
     * 跨省医保市编码
     */
    private String siCityCode = null;

    /**
     * 跨省医保市名称
     */
    private String siCityName = null;

    /**
     * 医保特殊备注
     */
    private String localExtFlag3 = null;

    /**
     * 是否是GCP患者，空值表示不是GCP患者，0取消GCP患者，1设置GCP患者。
     */
    private Boolean gcpInpatient = null;

    /**
     * 绿色通道标志 1是 0不是
     */
    private Boolean greenRoad = null;

    /**
     * VIP患者标志 1是 0不是
     */
    private Boolean vipFlag = null;

    /**
     * 是否是ERAS患者，空值表示不是ERAS患者，0取消ERAS患者，1设置ERAS患者。
     */
    private Boolean erasInpatient = null;

    /**
     * VTE等级
     */
    private String vte = null;

    /**
     * VTE等级是否被医生确认：0 未确认；1 已确认
     */
    private Boolean vteCfm = null;

    /**
     * 门诊号
     */
    private String clinicCode = null;

    /**
     * 住院预约发生序号
     */
    private Integer happenNo = null;

    /**
     * 医疗照顾人员类别
     */
    private String medicalStaff = null;

    /**
     * 备注
     */
    private String remark = null;

    /**
     * 
     */
    private String caseApproveFlag = null;

    /**
     *
     */
    private String caseReason = null;
}
