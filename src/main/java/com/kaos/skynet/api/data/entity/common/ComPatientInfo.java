package com.kaos.skynet.api.data.entity.common;

import java.time.LocalDateTime;
import java.util.Date;

import com.kaos.skynet.api.data.enums.BloodTypeEnum;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.enums.common.HealthCodeEnum;
import com.kaos.skynet.api.enums.common.TravelCodeEnum;
import com.kaos.skynet.api.enums.common.ValidStateEnum;

import lombok.Data;

/**
 * 实体：患者信息（XYHIS.COM_PATIENTINFO）
 */
@Data
public class ComPatientInfo {
    /**
     * 就诊卡号
     */
    private String cardNo = null;

    /**
     * 电脑号
     */
    private String icCardNo = null;

    /**
     * 患者姓名
     */
    private String name = null;

    /**
     * 拼音码
     */
    private String spellCode = null;

    /**
     * 五笔码
     */
    private String wbCode = null;

    /**
     * 生日
     */
    private LocalDateTime birthday = null;

    /**
     * 性别
     */
    private SexEnum sex = null;

    /**
     * 身份证号
     */
    private String identityCardNo = null;

    /**
     * 血型
     */
    private BloodTypeEnum bloodCode = null;

    /**
     * 职业
     */
    private String profCode = null;

    /**
     * 工作单位
     */
    private String workHome = null;

    /**
     * 单位电话
     */
    private String workTel = null;

    /**
     * 单位邮编
     */
    private String workZip = null;

    /**
     * 户口和家庭所在
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
    private String district = null;

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
     * 联系人住址
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
     * 结算类别
     */
    private String payKindCode = null;

    /**
     * 结算类别名
     */
    private String payKindName = null;

    /**
     * 合同单位编码
     */
    private String pactCode = null;

    /**
     * 合同单位名称
     */
    private String pactName = null;

    /**
     * 医疗证号
     */
    private String medCardNo = null;

    /**
     * 出生地
     */
    private String areaCode = null;

    /**
     * 医疗费用
     */
    private String framt = null;

    /**
     * 药物过敏
     */
    private Boolean anaphyFlag = null;

    /**
     * 重要疾病
     */
    private Boolean hepatitisFlag = null;

    /**
     * 账户密码
     */
    private String passwd = null;

    /**
     * 账户总额
     */
    private Double accountAmt = null;

    /**
     * 上期账户结余
     */
    private Double lastSum = null;

    /**
     * 上期银行结余
     */
    private Double lastBankSum = null;

    /**
     * 欠费次数
     */
    private Integer arrearTimes = null;

    /**
     * 欠费金额
     */
    private Double arrearSum = null;

    /**
     * 入院来源
     */
    private String inHosSource = null;

    /**
     * 最近一次入院日期
     */
    private Date lastInHosDate = null;

    /**
     * 住院次数
     */
    private Integer inHosTimes = null;

    /**
     * 最近一次出院日期
     */
    private Date lastOutHosDate = null;

    /**
     * 初诊日期
     */
    private Date firstSeeDate = null;

    /**
     * 最近挂号日期
     */
    private Date lastRegDate = null;

    /**
     * 违约次数
     */
    private Integer disobyCnt = null;

    /**
     * 结束日期
     */
    private Date endDate = null;

    /**
     * 备注
     */
    private String mark = null;

    /**
     * 操作员
     */
    private String operCode = null;

    /**
     * 操作时间
     */
    private Date operDate = null;

    /**
     * 有效性
     */
    private ValidStateEnum isValid = null;

    /**
     * 算法类别
     */
    private String feeKind = null;

    /**
     * 旧卡号
     */
    private String oldCardNo = null;

    /**
     * 是否加密姓名
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
     * 是否保健对象
     */
    private Boolean vipFlag = null;

    /**
     * 母亲姓名
     */
    private String motherName = null;

    /**
     * 是否急诊
     */
    private Boolean isTreatment = null;

    /**
     * 病案号
     */
    private String caseNo = null;

    /**
     * 保险公司编码
     */
    private String insuranceId = null;

    /**
     * 保险公司名称
     */
    private String insuranceName = null;

    /**
     * 家庭住址门牌号
     */
    private String homeDoorNo = null;

    /**
     * 联系人门牌号
     */
    private String linkmanDoorNo = null;

    /**
     * 电子邮件
     */
    private String email = null;

    /**
     * EMR患者基本信息流水号
     */
    private String emrPatId = null;

    /**
     * 患者来源：2为北区
     */
    private String patientSource = null;

    /**
     * 院区标识
     */
    private DeptOwnEnum detpOwn = null;

    /**
     * 绿通标识
     */
    private Boolean greenRoad = null;

    /**
     * 创建日期
     */
    private Date createDate = null;

    /**
     * 是否发热患者
     */
    private String is2019ncov = null;

    /**
     * 联系人证件类型
     */
    private String linkmanIdenType = null;

    /**
     * 联系人身份证号码
     */
    private String linkmanIdenNo = null;

    /**
     * 是否实名制
     */
    private Boolean idenFlag = null;

    /**
     * 省份
     */
    private String province = null;

    /**
     * 城市
     */
    private String city = null;

    /**
     * 区
     */
    private String area = null;

    /**
     * 是否院内VIP
     */
    private Boolean vip2Flag = null;

    /**
     * 普通患者\生殖公益患者\取消生殖公益患者
     */
    private String reproductiveFund = null;

    /**
     * 陪护人员卡号
     */
    private String accompanyCardNo = null;

    /**
     * 是否为职员
     */
    private Boolean isEmpl = null;

    /**
     * 平台IIB
     */
    private String empi = null;

    /**
     * 员工编码
     */
    private String emplCode = null;

    /**
     * GCP患者标识（临床试验）
     */
    private Boolean gcpFlag = null;

    /**
     * 平台移植未知字段
     */
    private String road = null;

    /**
     * 村
     */
    private String village = null;

    /**
     * 乡镇
     */
    private String townShip = null;

    /**
     * 健康码
     */
    private HealthCodeEnum healthCode = null;

    /**
     * 行程码
     */
    private TravelCodeEnum travelCode = null;

    /**
     * 是否到过高风险地区标识
     */
    private Boolean highRiskFlag = null;

    /**
     * 高风险地区列表
     */
    private String highRiskArea = null;
}
