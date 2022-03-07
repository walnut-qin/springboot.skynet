package com.kaos.skynet.entity.common;

import java.util.Date;
import java.util.List;

import com.kaos.skynet.enums.impl.common.BloodTypeEnum;
import com.kaos.skynet.enums.impl.common.DeptOwnEnum;
import com.kaos.skynet.enums.impl.common.SexEnum;
import com.kaos.skynet.enums.impl.common.ValidStateEnum;

/**
 * 实体：患者信息（XYHIS.COM_PATIENTINFO）
 */
public class ComPatientInfo {
    /**
     * 就诊卡号
     */
    public String cardNo = null;

    /**
     * 电脑号
     */
    public String icCardNo = null;

    /**
     * 患者姓名
     */
    public String name = null;

    /**
     * 拼音码
     */
    public String spellCode = null;

    /**
     * 五笔码
     */
    public String wbCode = null;

    /**
     * 生日
     */
    public Date birthday = null;

    /**
     * 性别
     */
    public SexEnum sex = null;

    /**
     * 身份证号
     */
    public String identityCardNo = null;

    /**
     * 血型
     */
    public BloodTypeEnum bloodCode = null;

    /**
     * 职业
     */
    public String profCode = null;

    /**
     * 工作单位
     */
    public String workHome = null;

    /**
     * 单位电话
     */
    public String workTel = null;

    /**
     * 单位邮编
     */
    public String workZip = null;

    /**
     * 户口和家庭所在
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
    public String district = null;

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
     * 联系人住址
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
     * 结算类别
     */
    public String payKindCode = null;

    /**
     * 结算类别名
     */
    public String payKindName = null;

    /**
     * 合同单位编码
     */
    public String pactCode = null;

    /**
     * 合同单位名称
     */
    public String pactName = null;

    /**
     * 医疗证号
     */
    public String medCardNo = null;

    /**
     * 出生地
     */
    public String areaCode = null;

    /**
     * 医疗费用
     */
    public String framt = null;

    /**
     * 药物过敏
     */
    public Boolean anaphyFlag = null;

    /**
     * 重要疾病
     */
    public Boolean hepatitisFlag = null;

    /**
     * 账户密码
     */
    public String passwd = null;

    /**
     * 账户总额
     */
    public Double accountAmt = null;

    /**
     * 上期账户结余
     */
    public Double lastSum = null;

    /**
     * 上期银行结余
     */
    public Double lastBankSum = null;

    /**
     * 欠费次数
     */
    public Integer arrearTimes = null;

    /**
     * 欠费金额
     */
    public Double arrearSum = null;

    /**
     * 入院来源
     */
    public String inHosSource = null;

    /**
     * 最近一次入院日期
     */
    public Date lastInHosDate = null;

    /**
     * 住院次数
     */
    public Integer inHosTimes = null;

    /**
     * 最近一次出院日期
     */
    public Date lastOutHosDate = null;

    /**
     * 初诊日期
     */
    public Date firstSeeDate = null;

    /**
     * 最近挂号日期
     */
    public Date lastRegDate = null;

    /**
     * 违约次数
     */
    public Integer disobyCnt = null;

    /**
     * 结束日期
     */
    public Date endDate = null;

    /**
     * 备注
     */
    public String mark = null;

    /**
     * 操作员 {@link ComPatientInfo.AssociateEntity#operEmpl}
     */
    public String operCode = null;

    /**
     * 操作时间
     */
    public Date operDate = null;

    /**
     * 有效性
     */
    public ValidStateEnum isValid = null;

    /**
     * 算法类别
     */
    public String feeKind = null;

    /**
     * 旧卡号
     */
    public String oldCardNo = null;

    /**
     * 是否加密姓名
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
     * 是否保健对象
     */
    public Boolean vipFlag = null;

    /**
     * 母亲姓名
     */
    public String motherName = null;

    /**
     * 是否急诊
     */
    public Boolean isTreatment = null;

    /**
     * 病案号
     */
    public String caseNo = null;

    /**
     * 保险公司编码
     */
    public String insuranceId = null;

    /**
     * 保险公司名称
     */
    public String insuranceName = null;

    /**
     * 家庭住址门牌号
     */
    public String homeDoorNo = null;

    /**
     * 联系人门牌号
     */
    public String linkmanDoorNo = null;

    /**
     * 电子邮件
     */
    public String email = null;

    /**
     * EMR患者基本信息流水号
     */
    public String emrPatId = null;

    /**
     * 患者来源：2为北区
     */
    public String patientSource = null;

    /**
     * 院区标识
     */
    public DeptOwnEnum detpOwn = null;

    /**
     * 绿通标识
     */
    public Boolean greenRoad = null;

    /**
     * 创建日期
     */
    public Date createDate = null;

    /**
     * 是否发热患者
     */
    public String is2019ncov = null;

    /**
     * 联系人证件类型
     */
    public String linkmanIdenType = null;

    /**
     * 联系人身份证号码
     */
    public String linkmanIdenNo = null;

    /**
     * 是否实名制
     */
    public Boolean idenFlag = null;

    /**
     * 省份
     */
    public String province = null;

    /**
     * 城市
     */
    public String city = null;

    /**
     * 区
     */
    public String area = null;

    /**
     * 是否院内VIP
     */
    public Boolean vip2Flag = null;

    /**
     * 普通患者\生殖公益患者\取消生殖公益患者
     */
    public String reproductiveFund = null;

    /**
     * 陪护人员卡号
     */
    public String accompanyCardNo = null;

    /**
     * 是否为职员
     */
    public Boolean isEmpl = null;

    /**
     * 平台IIB
     */
    public String empi = null;

    /**
     * 员工编码
     */
    public String emplCode = null;

    /**
     * GCP患者标识（临床试验）
     */
    public Boolean gcpFlag = null;

    /**
     * 平台移植未知字段
     */
    public String road = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 基本信息操作员 {@link ComPatientInfo#operCode}
         */
        public DawnOrgEmpl operEmpl = null;

        /**
         * 陪护的患者 {@link}
         */
        public List<ComPatientInfo> escortedPatients = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
