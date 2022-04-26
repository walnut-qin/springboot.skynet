package com.kaos.skynet.entity.inpatient;

import java.time.LocalDateTime;
import java.util.Date;

import com.kaos.skynet.entity.common.ComPatientInfo;
import com.kaos.skynet.entity.common.DawnOrgDept;
import com.kaos.skynet.entity.common.DawnOrgEmpl;
import com.kaos.skynet.entity.outpatient.FinOprRegister;
import com.kaos.skynet.enums.impl.common.BloodTypeEnum;
import com.kaos.skynet.enums.impl.common.DeptOwnEnum;
import com.kaos.skynet.enums.impl.common.PayKindEnum;
import com.kaos.skynet.enums.impl.common.SexEnum;
import com.kaos.skynet.enums.impl.inpatient.InAvenueEnum;
import com.kaos.skynet.enums.impl.inpatient.InCircsEnum;
import com.kaos.skynet.enums.impl.inpatient.InSourceEnum;
import com.kaos.skynet.enums.impl.inpatient.InStateEnum;

/**
 * 住院主表信息(XYHIS.FIN_IPR_INMAININFO)
 */
public class FinIprInMainInfo {
    /**
     * 住院流水号
     */
    public String inpatientNo = null;

    /**
     *
     */
    public String medicalType = null;

    /**
     * 住院号
     */
    public String patientNo = null;

    /**
     * 就诊卡号 {@link FinIprInMainInfo.AssociateEntity#patientInfo}
     */
    public String cardNo = null;

    /**
     * 社保卡号
     */
    public String medCardNo = null;

    /**
     * 姓名
     */
    public String name = null;

    /**
     * 性别
     */
    public SexEnum sex = null;

    /**
     * 身份证号
     */
    public String idenNo = null;

    /**
     * 拼写码
     */
    public String spellCode = null;

    /**
     * 生日
     */
    public LocalDateTime birthday = null;

    /**
     * 职业代码
     */
    public String profCode = null;

    /**
     * 工作单位
     */
    public String workName = null;

    /**
     * 工作电话
     */
    public String workTel = null;

    /**
     * 工作邮编
     */
    public String workZip = null;

    /**
     * 家庭住址
     */
    public String home = null;

    /**
     * 家庭电话
     */
    public String homeTel = null;

    /**
     * 家庭邮编
     */
    public String homeZip = null;

    /**
     * 籍贯
     */
    public String dist = null;

    /**
     * 出生地代码
     */
    public String birthArea = null;

    /**
     * 民族
     */
    public String nationCode = null;

    /**
     * 联系人姓名
     */
    public String linkmanName = null;

    /**
     * 联系人电话
     */
    public String linkmanTel = null;

    /**
     * 联系人地址
     */
    public String linkmanAddress = null;

    /**
     * 联系人关系
     */
    public String linkmanRelation = null;

    /**
     * 婚姻状况
     */
    public String mari = null;

    /**
     * 国籍
     */
    public String country = null;

    /**
     * 身高
     */
    public Double height = null;

    /**
     * 体重
     */
    public Double weight = null;

    /**
     * 血压
     */
    public String bloodDress = null;

    /**
     * 血型
     */
    public BloodTypeEnum bloodCode = null;

    /**
     * 重大疾病编码
     */
    public Boolean hepatitisFlag = null;

    /**
     * 过敏标志
     */
    public Boolean anaphyFlag = null;

    /**
     * 入院日期
     */
    public Date inDate = null;

    /**
     * 科室代码 {@link FinIprInMainInfo.AssociateEntity#dept}
     */
    public String deptCode = null;

    /**
     * 科室名称
     */
    public String deptName = null;

    /**
     * 结算类型
     */
    public PayKindEnum payKind = null;

    /**
     * 合同单位编码
     */
    public String pactCode = null;

    /**
     * 合同单位名
     */
    public String pactName = null;

    /**
     * 床位号 {@link FinIprInMainInfo.AssociateEntity#bedInfo}
     */
    public String bedNo = null;

    /**
     * 病区编码 {@link FinIprInMainInfo.AssociateEntity#nurseCell}
     */
    public String nurseCellCode = null;

    /**
     * 病区名称
     */
    public String nurseCellName = null;

    /**
     * 住院医师代码 {@link FinIprInMainInfo.AssociateEntity#houseDoc}
     */
    public String houseDocCode = null;

    /**
     * 住院医师姓名
     */
    public String houseDocName = null;

    /**
     * 主治医师代码 {@link FinIprInMainInfo.AssociateEntity#chargeDoc}
     */
    public String chargeDocCode = null;

    /**
     * 主治医师姓名
     */
    public String chargeDocName = null;

    /**
     * 主任医师代码 {@link FinIprInMainInfo.AssociateEntity#chiefDoc}
     */
    public String chiefDocCode = null;

    /**
     * 主任医师姓名
     */
    public String chiefDocName = null;

    /**
     * 责任护士编码 {@link FinIprInMainInfo.AssociateEntity#dutyNurse}
     */
    public String dutyNurseCode = null;

    /**
     * 责任护士姓名
     */
    public String dutyNurseName = null;

    /**
     * 入院情况
     */
    public InCircsEnum inCircs = null;

    /**
     * 入院途径
     */
    public InAvenueEnum inAvenue = null;

    /**
     * 入院来源
     */
    public InSourceEnum inSourceEnum = null;

    /**
     * 入院次数
     */
    public Integer inTimes = null;

    /**
     * 预交金额(未结)
     */
    public Double prepayCost = null;

    /**
     * 转入预交金额（未结)
     */
    public Double changePrepayCost = null;

    /**
     * 警戒线
     */
    public Double moneyAlert = null;

    /**
     * 总费用
     */
    public Double totCost = null;

    /**
     * 自费金额
     */
    public Double ownCost = null;

    /**
     * 自付金额(未结)
     */
    public Double payCost = null;

    /**
     * 统筹金额(未结)
     */
    public Double pubCost = null;

    /**
     * 优惠金额(未结)
     */
    public Double ecoCost = null;

    /**
     * 余额(未结)
     */
    public Double feeCost = null;

    /**
     * 转入费用金额(未结)
     */
    public Double changeTotCost = null;

    /**
     * 待遇上限
     */
    public Double upperLimit = null;

    /**
     * 固定费用间隔天数
     */
    public Integer feeInterval = null;

    /**
     * 结算序号
     */
    public Integer balanceNo = null;

    /**
     * 费用金额(已结)
     */
    public Double balanceCost = null;

    /**
     * 预交金额(已结)
     */
    public Double balancePrepay = null;

    /**
     * 结算日期
     */
    public Date balanceDate = null;

    /**
     * 是否关账
     */
    public Boolean stopAccount = null;

    /**
     * 是否为婴儿
     */
    public Boolean babyFlag = null;

    /**
     * 病案状态: 0 无需病案 1 需要病案 2 医生站形成病案 3 病案室形成病案 4病案封存
     */
    public String caseFlag = null;

    /**
     * 在院状态
     */
    public InStateEnum inState = null;

    /**
     * 是否请假
     */
    public Boolean leaveFlag = null;

    /**
     * 出院日期(预约)
     */
    public Date prepayOutDate = null;

    /**
     *
     */
    public Date outDate = null;

    /**
     * 转归代号
     */
    public String zg = null;

    /**
     * 开据医师 {@link FinIprInMainInfo.AssociateEntity#empl}
     */
    public String emplCode = null;

    /**
     * 是否在ICU
     */
    public Boolean inICU = null;

    /**
     * 病案送入病案室否0未1送
     */
    public Boolean caseSendFlag = null;

    /**
     * 护理级别(TEND):名称显示护理级别名称(一级护理，二级护理，三级护理)
     */
    public String tend = null;

    /**
     * 病危：0 普通 1 病重 2 病危
     */
    public String criticalFlag = null;

    /**
     * 上次固定费用时间
     */
    public Date prefixFeeDate = null;

    /**
     * 操作员 {@link FinIprInMainInfo.AssociateEntity#empl}
     */
    public String operCode = null;

    /**
     *
     */
    public Date operDate = null;

    /**
     * 血滞纳金
     */
    public Double bloodLateFee = null;

    /**
     * 公费患者日限额
     */
    public Double dayLimit = null;

    /**
     * 公费患者日限额累计
     */
    public Double limitTot = null;

    /**
     * 公费患者日限额超标部分金额
     */
    public Double limitOverTop = null;

    /**
     * 门诊诊断
     */
    public String clinicDiagnose = null;

    /**
     * 生育保险患者电脑号(医保用作欠费标记1为欠费)
     */
    public String procreatePcNo = null;

    /**
     * 饮食
     */
    public String dieteticMark = null;

    /**
     * 公费患者公费药品累计(日限额)
     */
    public Double bursaryTotMedFee = null;

    /**
     * 备注
     */
    public String memo = null;

    /**
     * 床位上限
     */
    public Double bedLimit = null;

    /**
     * 空调上限
     */
    public Double airLimit = null;

    /**
     * 床费超标处理 0超标不限 1超标自理 2超标不计
     */
    public String bedOverDeal = null;

    /**
     * 扩展标记[无费入院标记]
     */
    public String extFlag = null;

    /**
     * 扩展标记1
     */
    public String extFlag1 = null;

    /**
     * 扩展标记2(襄阳保存特殊自费类型:火灾，弃婴，车祸等)
     */
    public String extFlag2 = null;

    /**
     * 膳食花费总额
     */
    public Double boardCost = null;

    /**
     * 膳食预交金额
     */
    public Double boardPrepay = null;

    /**
     * 膳食结算状态：0在院 1出院
     */
    public String boardState = null;

    /**
     * 自费比例
     */
    public Double ownRate = null;

    /**
     * 自付比例
     */
    public Double payRate = null;

    /**
     * 扩展数值（中山一用作－剩余统筹金额）
     */
    public Double extNumber = null;

    /**
     * 扩展编码（襄阳保存医保特殊记录：15r重复住院等）
     */
    public String extCode = null;

    /**
     * 诊断名称（建议用此保存主诊断）
     */
    public String diagName = null;

    /**
     * 是否加密
     */
    public Boolean isEncryptName = null;

    /**
     * 密文
     */
    public String normalName = null;

    /**
     * 证件类型
     */
    public String idCardType = null;

    /**
     * M 金额 D时间段
     */
    public String alterType = null;

    /**
     * 警戒线开始时间
     */
    public Date alterBegin = null;

    /**
     * 警戒线结束时间
     */
    public Date alterEnd = null;

    /**
     * 警戒线批准人 {@link FinIprInMainInfo.AssociateEntity#alterApproveEmpl}
     */
    public String alterApproveCode = null;

    /**
     * 警戒线批准原因
     */
    public Date alterApproveDate = null;

    /**
     * 人民医院旧系统资格证书编号(导数据用)[襄阳暂存质控护士编码]
     */
    public String oldSiNo = null;

    /**
     * 患者医保审核确认标记
     */
    public String localExtFlag1 = null;

    /**
     * 医保普通备注
     */
    public String localExtFlag2 = null;

    /**
     * EMR住院流水号 SEQ_FIN_INPATIENTID
     */
    public String emrInpatientId = null;

    /**
     * 是否是单病种患者0：非单病种1：单病种
     */
    public Boolean isSingleDis = null;

    /**
     * 单病种ICD编码
     */
    public String singleDisId = null;

    /**
     * 定额结算手术名称
     */
    public String diseaseName = null;

    /**
     * 定额结算手术定额
     */
    public Double diseaseCost = null;

    /**
     * 材料费用
     */
    public String materialCost = null;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    public DeptOwnEnum deptOwn = null;

    /**
     * 急诊绿色通道结账
     */
    public Boolean isGreenBalance = null;

    /**
     * 跨省医保省编码
     */
    public String siProvinceCode = null;

    /**
     * 跨省医保省名称
     */
    public String siProvinceName = null;

    /**
     * 跨省医保市编码
     */
    public String siCityCode = null;

    /**
     * 跨省医保市名称
     */
    public String siCityName = null;

    /**
     * 医保特殊备注
     */
    public String localExtFlag3 = null;

    /**
     * 是否是GCP患者，空值表示不是GCP患者，0取消GCP患者，1设置GCP患者。
     */
    public Boolean gcpInpatient = null;

    /**
     * 绿色通道标志 1是 0不是
     */
    public Boolean greenRoad = null;

    /**
     * VIP患者标志 1是 0不是
     */
    public Boolean vipFlag = null;

    /**
     * 是否是ERAS患者，空值表示不是ERAS患者，0取消ERAS患者，1设置ERAS患者。
     */
    public Boolean erasInpatient = null;

    /**
     * VTE等级
     */
    public String vte = null;

    /**
     * VTE等级是否被医生确认：0 未确认；1 已确认
     */
    public Boolean vteCfm = null;

    /**
     * 门诊号 {@link FinIprInMainInfo.AssociateEntity#register}
     */
    public String clinicCode = null;

    /**
     * 住院预约发生序号
     */
    public Integer happenNo = null;

    /**
     * 医疗照顾人员类别
     */
    public String medicalStaff = null;

    /**
     * 备注
     */
    public String remark = null;

    /**
     * 
     */
    public String caseApproveFlag = null;

    /**
     *
     */
    public String caseReason = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 患者基本信息 {@link FinIprInMainInfo#cardNo}
         */
        public ComPatientInfo patientInfo = null;

        /**
         * 科室信息 {@link FinIprInMainInfo#deptCode}
         */
        public DawnOrgDept dept = null;

        /**
         * 床位信息 {@link FinIprInMainInfo#bedNo}
         */
        public ComBedInfo bedInfo = null;

        /**
         * 病区信息 {@link FinIprInMainInfo#nurseCellCode}
         */
        public DawnOrgDept nurseCell = null;

        /**
         * 住院医师 {@link FinIprInMainInfo#houseDocCode}
         */
        public DawnOrgEmpl houseDoc = null;

        /**
         * 主治医师 {@link FinIprInMainInfo#chargeDocCode}
         */
        public DawnOrgEmpl chargeDoc = null;

        /**
         * 主治医师 {@link FinIprInMainInfo#chiefDocCode}
         */
        public DawnOrgEmpl chiefDoc = null;

        /**
         * 主治医师 {@link FinIprInMainInfo#dutyNurseCode}
         */
        public DawnOrgEmpl dutyNurse = null;

        /**
         * 开据医师 {@link FinIprInMainInfo#emplCode}
         */
        public DawnOrgEmpl empl = null;

        /**
         * 操作员 {@link FinIprInMainInfo#operCode}
         */
        public DawnOrgEmpl operEmpl = null;

        /**
         * 操作员 {@link FinIprInMainInfo#alterApproveCode}
         */
        public DawnOrgEmpl alterApproveEmpl = null;

        /**
         * 挂号记录 {@link FinIprInMainInfo#clinicCode}
         */
        public FinOprRegister register = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
