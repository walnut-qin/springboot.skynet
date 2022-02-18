package com.kaos.his.entity.inpatient;

import java.util.Date;

import com.kaos.his.entity.common.ComPatientInfo;
import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.his.entity.common.DawnOrgEmpl;
import com.kaos.his.enums.common.BloodTypeEnum;
import com.kaos.his.enums.common.PayKindEnum;
import com.kaos.his.enums.common.SexEnum;
import com.kaos.his.enums.inpatient.InAvenueEnum;
import com.kaos.his.enums.inpatient.InCircsEnum;
import com.kaos.his.enums.inpatient.InSourceEnum;

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
    public Date birthday = null;

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
     *
     */
    public String BALANCE_DATE = null;

    /**
     *
     */
    public String STOP_ACOUNT = null;

    /**
     *
     */
    public String BABY_FLAG = null;

    /**
     *
     */
    public String CASE_FLAG = null;

    /**
     *
     */
    public String IN_STATE = null;

    /**
     *
     */
    public String LEAVE_FLAG = null;

    /**
     *
     */
    public String PREPAY_OUTDATE = null;

    /**
     *
     */
    public String OUT_DATE = null;

    /**
     *
     */
    public String ZG = null;

    /**
     *
     */
    public String EMPL_CODE = null;

    /**
     *
     */
    public String IN_ICU = null;

    /**
     *
     */
    public String CASESEND_FLAG = null;

    /**
     *
     */
    public String TEND = null;

    /**
     *
     */
    public String CRITICAL_FLAG = null;

    /**
     *
     */
    public String PREFIXFEE_DATE = null;

    /**
     *
     */
    public String OPER_CODE = null;

    /**
     *
     */
    public String OPER_DATE = null;

    /**
     *
     */
    public String BLOOD_LATEFEE = null;

    /**
     *
     */
    public String DAY_LIMIT = null;

    /**
     *
     */
    public String LIMIT_TOT = null;

    /**
     *
     */
    public String LIMIT_OVERTOP = null;

    /**
     *
     */
    public String CLINIC_DIAGNOSE = null;

    /**
     *
     */
    public String PROCREATE_PCNO = null;

    /**
     *
     */
    public String DIETETIC_MARK = null;

    /**
     *
     */
    public String BURSARY_TOTMEDFEE = null;

    /**
     *
     */
    public String MEMO = null;

    /**
     *
     */
    public String BED_LIMIT = null;

    /**
     *
     */
    public String AIR_LIMIT = null;

    /**
     *
     */
    public String BEDOVERDEAL = null;

    /**
     *
     */
    public String EXT_FLAG = null;

    /**
     *
     */
    public String EXT_FLAG1 = null;

    /**
     *
     */
    public String EXT_FLAG2 = null;

    /**
     *
     */
    public String BOARD_COST = null;

    /**
     *
     */
    public String BOARD_PREPAY = null;

    /**
     *
     */
    public String BOARD_STATE = null;

    /**
     *
     */
    public String OWN_RATE = null;

    /**
     *
     */
    public String PAY_RATE = null;

    /**
     *
     */
    public String EXT_NUMBER = null;

    /**
     *
     */
    public String EXT_CODE = null;

    /**
     *
     */
    public String DIAG_NAME = null;

    /**
     *
     */
    public String IS_ENCRYPTNAME = null;

    /**
     *
     */
    public String NORMALNAME = null;

    /**
     *
     */
    public String IDCARDTYPE = null;

    /**
     *
     */
    public String ALTER_TYPE = null;

    /**
     *
     */
    public String ALTER_BEGIN = null;

    /**
     *
     */
    public String ALTER_END = null;

    /**
     *
     */
    public String ALTER_APPROVE_CODE = null;

    /**
     *
     */
    public String ALTER_APPROVE_DATE = null;

    /**
     *
     */
    public String OLDSI_NO = null;

    /**
     *
     */
    public String LOCAL_EXT_FLAG1 = null;

    /**
     *
     */
    public String LOCAL_EXT_FLAG2 = null;

    /**
     *
     */
    public String EMR_INPATIENTID = null;

    /**
     *
     */
    public String IS_SINGLEDIS = null;

    /**
     *
     */
    public String SINGLEDIS_ID = null;

    /**
     *
     */
    public String DISEASENAME = null;

    /**
     *
     */
    public String DISEASECOST = null;

    /**
     *
     */
    public String MATERIALCOST = null;

    /**
     *
     */
    public String DEPTOWN = null;

    /**
     *
     */
    public String IS_GREEN_BALANCE = null;

    /**
     *
     */
    public String SI_PROVINCCODE = null;

    /**
     *
     */
    public String SI_PROVINCNAME = null;

    /**
     *
     */
    public String SI_CITYCODE = null;

    /**
     *
     */
    public String SI_CITYNAME = null;

    /**
     *
     */
    public String LOCAL_EXT_FLAG3 = null;

    /**
     *
     */
    public String GCPINPATIENT = null;

    /**
     *
     */
    public String GREENROAD = null;

    /**
     *
     */
    public String VIP_FLAG = null;

    /**
     *
     */
    public String ERASINPATIENT = null;

    /**
     *
     */
    public String VTE = null;

    /**
     *
     */
    public String VTE_CFM = null;

    /**
     *
     */
    public String CLINIC_CODE = null;

    /**
     *
     */
    public String HAPPEN_NO = null;

    /**
     *
     */
    public String MEDICAL_STAFF = null;

    /**
     *
     */
    public String REMARK = null;

    /**
     *
     */
    public String CASE_APPROVE_FLAG = null;

    /**
     *
     */
    public String CASE_REASON = null;

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
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
