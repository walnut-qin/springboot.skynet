package com.kaos.skynet.api.data.entity.common;

import java.time.LocalDateTime;
import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.enums.BloodTypeEnum;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.HealthCodeEnum;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.enums.common.TravelCodeEnum;
import com.kaos.skynet.api.enums.common.ValidStateEnum;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体：患者信息（XYHIS.COM_PATIENTINFO）
 */
@Getter
@Setter
@Builder
public class ComPatientInfo {
    /**
     * 就诊卡号
     */
    private String cardNo;

    /**
     * 电脑号
     */
    private String icCardNo;

    /**
     * 患者姓名
     */
    private String name;

    /**
     * 拼音码
     */
    private String spellCode;

    /**
     * 五笔码
     */
    private String wbCode;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 身份证号
     */
    private String identityCardNo;

    /**
     * 血型
     */
    private BloodTypeEnum bloodCode;

    /**
     * 职业
     */
    private String profCode;

    /**
     * 工作单位
     */
    private String workHome;

    /**
     * 单位电话
     */
    private String workTel;

    /**
     * 单位邮编
     */
    private String workZip;

    /**
     * 户口和家庭所在
     */
    private String home;

    /**
     * 家庭电话
     */
    private String homeTel;

    /**
     * 家庭邮编
     */
    private String homeZip;

    /**
     * 籍贯
     */
    private String district;

    /**
     * 民族
     */
    private String nationCode;

    /**
     * 联系人姓名
     */
    private String linkmanName;

    /**
     * 联系人电话
     */
    private String linkmanTel;

    /**
     * 联系人住址
     */
    private String linkmanAddress;

    /**
     * 联系人关系
     */
    private String linkmanRelation;

    /**
     * 婚姻状况
     */
    private String mari;

    /**
     * 国籍
     */
    private String country;

    /**
     * 结算类别
     */
    private String payKindCode;

    /**
     * 结算类别名
     */
    private String payKindName;

    /**
     * 合同单位编码
     */
    private String pactCode;

    /**
     * 合同单位名称
     */
    private String pactName;

    /**
     * 医疗证号
     */
    private String medCardNo;

    /**
     * 出生地
     */
    private String areaCode;

    /**
     * 医疗费用
     */
    private String framt;

    /**
     * 药物过敏
     */
    private Boolean anaphyFlag;

    /**
     * 重要疾病
     */
    private Boolean hepatitisFlag;

    /**
     * 账户密码
     */
    private String passwd;

    /**
     * 账户总额
     */
    private Double accountAmt;

    /**
     * 上期账户结余
     */
    private Double lastSum;

    /**
     * 上期银行结余
     */
    private Double lastBankSum;

    /**
     * 欠费次数
     */
    private Integer arrearTimes;

    /**
     * 欠费金额
     */
    private Double arrearSum;

    /**
     * 入院来源
     */
    private String inHosSource;

    /**
     * 最近一次入院日期
     */
    private Date lastInHosDate;

    /**
     * 住院次数
     */
    private Integer inHosTimes;

    /**
     * 最近一次出院日期
     */
    private Date lastOutHosDate;

    /**
     * 初诊日期
     */
    private Date firstSeeDate;

    /**
     * 最近挂号日期
     */
    private Date lastRegDate;

    /**
     * 违约次数
     */
    private Integer disobyCnt;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 备注
     */
    private String mark;

    /**
     * 操作员
     */
    private String operCode;

    /**
     * 操作时间
     */
    private Date operDate;

    /**
     * 有效性
     */
    private ValidStateEnum isValid;

    /**
     * 算法类别
     */
    private String feeKind;

    /**
     * 旧卡号
     */
    private String oldCardNo;

    /**
     * 是否加密姓名
     */
    private Boolean isEncryptName;

    /**
     * 密文
     */
    private String normalName;

    /**
     * 证件类型
     */
    private String idCardType;

    /**
     * 是否保健对象
     */
    private Boolean vipFlag;

    /**
     * 母亲姓名
     */
    private String motherName;

    /**
     * 是否急诊
     */
    private Boolean isTreatment;

    /**
     * 病案号
     */
    private String caseNo;

    /**
     * 保险公司编码
     */
    private String insuranceId;

    /**
     * 保险公司名称
     */
    private String insuranceName;

    /**
     * 家庭住址门牌号
     */
    private String homeDoorNo;

    /**
     * 联系人门牌号
     */
    private String linkmanDoorNo;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * EMR患者基本信息流水号
     */
    private String emrPatId;

    /**
     * 患者来源：2为北区
     */
    private String patientSource;

    /**
     * 院区标识
     */
    private DeptOwnEnum detpOwn;

    /**
     * 绿通标识
     */
    private Boolean greenRoad;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 是否发热患者
     */
    private String is2019ncov;

    /**
     * 联系人证件类型
     */
    private String linkmanIdenType;

    /**
     * 联系人身份证号码
     */
    private String linkmanIdenNo;

    /**
     * 是否实名制
     */
    private Boolean idenFlag;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 是否院内VIP
     */
    private Boolean vip2Flag;

    /**
     * 普通患者\生殖公益患者\取消生殖公益患者
     */
    private String reproductiveFund;

    /**
     * 陪护人员卡号
     */
    private String accompanyCardNo;

    /**
     * 是否为职员
     */
    private Boolean isEmpl;

    /**
     * 平台IIB
     */
    private String empi;

    /**
     * 员工编码
     */
    private String emplCode;

    /**
     * GCP患者标识（临床试验）
     */
    private Boolean gcpFlag;

    /**
     * 平台移植未知字段
     */
    private String road;

    /**
     * 村
     */
    private String village;

    /**
     * 乡镇
     */
    private String townShip;

    /**
     * 健康码
     */
    private HealthCodeEnum healthCode;

    /**
     * 行程码
     */
    private TravelCodeEnum travelCode;

    /**
     * 是否到过高风险地区标识
     */
    private Boolean highRiskFlag;

    /**
     * 高风险地区列表
     */
    private String highRiskArea;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof ComPatientInfo) {
            var that = (ComPatientInfo) arg0;
            return StringUtils.equals(this.cardNo, that.cardNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cardNo);
    }
}
