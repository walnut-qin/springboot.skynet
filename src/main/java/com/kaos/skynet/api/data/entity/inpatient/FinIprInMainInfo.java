package com.kaos.skynet.api.data.entity.inpatient;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.enums.BloodTypeEnum;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.enums.common.PayKindEnum;
import com.kaos.skynet.api.enums.inpatient.InAvenueEnum;
import com.kaos.skynet.api.enums.inpatient.InCircsEnum;
import com.kaos.skynet.api.enums.inpatient.InSourceEnum;
import com.kaos.skynet.api.enums.inpatient.InStateEnum;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Data;

/**
 * 住院主表信息(XYHIS.FIN_IPR_INMAININFO)
 */
@Data
@Builder
public class FinIprInMainInfo {
    /**
     * 住院流水号
     */
    private String inpatientNo;

    /**
     *
     */
    private String medicalType;

    /**
     * 住院号
     */
    private String patientNo;

    /**
     * 就诊卡号
     */
    private String cardNo;

    /**
     * 社保卡号
     */
    private String medCardNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 身份证号
     */
    private String idenNo;

    /**
     * 拼写码
     */
    private String spellCode;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 职业代码
     */
    private String profCode;

    /**
     * 工作单位
     */
    private String workName;

    /**
     * 工作电话
     */
    private String workTel;

    /**
     * 工作邮编
     */
    private String workZip;

    /**
     * 家庭住址
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
    private String dist;

    /**
     * 出生地代码
     */
    private String birthArea;

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
     * 联系人地址
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
     * 身高
     */
    private Double height;

    /**
     * 体重
     */
    private Double weight;

    /**
     * 血压
     */
    private String bloodDress;

    /**
     * 血型
     */
    private BloodTypeEnum bloodCode;

    /**
     * 重大疾病编码
     */
    private Boolean hepatitisFlag;

    /**
     * 过敏标志
     */
    private Boolean anaphyFlag;

    /**
     * 入院日期
     */
    private LocalDateTime inDate;

    /**
     * 科室代码
     */
    private String deptCode;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 结算类型
     */
    private PayKindEnum payKind;

    /**
     * 合同单位编码
     */
    private String pactCode;

    /**
     * 合同单位名
     */
    private String pactName;

    /**
     * 床位号
     */
    private String bedNo;

    /**
     * 病区编码
     */
    private String nurseCellCode;

    /**
     * 病区名称
     */
    private String nurseCellName;

    /**
     * 住院医师代码
     */
    private String houseDocCode;

    /**
     * 住院医师姓名
     */
    private String houseDocName;

    /**
     * 主治医师代码
     */
    private String chargeDocCode;

    /**
     * 主治医师姓名
     */
    private String chargeDocName;

    /**
     * 主任医师代码
     */
    private String chiefDocCode;

    /**
     * 主任医师姓名
     */
    private String chiefDocName;

    /**
     * 责任护士编码
     */
    private String dutyNurseCode;

    /**
     * 责任护士姓名
     */
    private String dutyNurseName;

    /**
     * 入院情况
     */
    private InCircsEnum inCircs;

    /**
     * 入院途径
     */
    private InAvenueEnum inAvenue;

    /**
     * 入院来源
     */
    private InSourceEnum inSourceEnum;

    /**
     * 入院次数
     */
    private Integer inTimes;

    /**
     * 预交金额(未结)
     */
    private Double prepayCost;

    /**
     * 转入预交金额（未结)
     */
    private Double changePrepayCost;

    /**
     * 警戒线
     */
    private Double moneyAlert;

    /**
     * 总费用
     */
    private Double totCost;

    /**
     * 自费金额
     */
    private Double ownCost;

    /**
     * 自付金额(未结)
     */
    private Double payCost;

    /**
     * 统筹金额(未结)
     */
    private Double pubCost;

    /**
     * 优惠金额(未结)
     */
    private Double ecoCost;

    /**
     * 余额(未结)
     */
    private Double feeCost;

    /**
     * 转入费用金额(未结)
     */
    private Double changeTotCost;

    /**
     * 待遇上限
     */
    private Double upperLimit;

    /**
     * 固定费用间隔天数
     */
    private Integer feeInterval;

    /**
     * 结算序号
     */
    private Integer balanceNo;

    /**
     * 费用金额(已结)
     */
    private Double balanceCost;

    /**
     * 预交金额(已结)
     */
    private Double balancePrepay;

    /**
     * 结算日期
     */
    private LocalDateTime balanceDate;

    /**
     * 是否关账
     */
    private Boolean stopAccount;

    /**
     * 是否为婴儿
     */
    private Boolean babyFlag;

    /**
     * 病案状态: 0 无需病案 1 需要病案 2 医生站形成病案 3 病案室形成病案 4病案封存
     */
    private String caseFlag;

    /**
     * 在院状态
     */
    private InStateEnum inState;

    /**
     * 是否请假
     */
    private Boolean leaveFlag;

    /**
     * 出院日期(预约)
     */
    private LocalDateTime prepayOutDate;

    /**
     *
     */
    private LocalDateTime outDate;

    /**
     * 转归代号
     */
    private String zg;

    /**
     * 开据医师
     */
    private String emplCode;

    /**
     * 是否在ICU
     */
    private Boolean inICU;

    /**
     * 病案送入病案室否0未1送
     */
    private Boolean caseSendFlag;

    /**
     * 护理级别(TEND):名称显示护理级别名称(一级护理，二级护理，三级护理)
     */
    private String tend;

    /**
     * 病危：0 普通 1 病重 2 病危
     */
    private String criticalFlag;

    /**
     * 上次固定费用时间
     */
    private LocalDateTime prefixFeeDate;

    /**
     * 操作员
     */
    private String operCode;

    /**
     *
     */
    private LocalDateTime operDate;

    /**
     * 血滞纳金
     */
    private Double bloodLateFee;

    /**
     * 公费患者日限额
     */
    private Double dayLimit;

    /**
     * 公费患者日限额累计
     */
    private Double limitTot;

    /**
     * 公费患者日限额超标部分金额
     */
    private Double limitOverTop;

    /**
     * 门诊诊断
     */
    private String clinicDiagnose;

    /**
     * 生育保险患者电脑号(医保用作欠费标记1为欠费)
     */
    private String procreatePcNo;

    /**
     * 饮食
     */
    private String dieteticMark;

    /**
     * 公费患者公费药品累计(日限额)
     */
    private Double bursaryTotMedFee;

    /**
     * 备注
     */
    private String memo;

    /**
     * 床位上限
     */
    private Double bedLimit;

    /**
     * 空调上限
     */
    private Double airLimit;

    /**
     * 床费超标处理 0超标不限 1超标自理 2超标不计
     */
    private String bedOverDeal;

    /**
     * 扩展标记[无费入院标记]
     */
    private String extFlag;

    /**
     * 扩展标记1
     */
    private String extFlag1;

    /**
     * 扩展标记2(襄阳保存特殊自费类型:火灾，弃婴，车祸等)
     */
    private String extFlag2;

    /**
     * 膳食花费总额
     */
    private Double boardCost;

    /**
     * 膳食预交金额
     */
    private Double boardPrepay;

    /**
     * 膳食结算状态：0在院 1出院
     */
    private String boardState;

    /**
     * 自费比例
     */
    private Double ownRate;

    /**
     * 自付比例
     */
    private Double payRate;

    /**
     * 扩展数值（中山一用作－剩余统筹金额）
     */
    private Double extNumber;

    /**
     * 扩展编码（襄阳保存医保特殊记录：15r重复住院等）
     */
    private String extCode;

    /**
     * 诊断名称（建议用此保存主诊断）
     */
    private String diagName;

    /**
     * 是否加密
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
     * M 金额 D时间段
     */
    private String alterType;

    /**
     * 警戒线开始时间
     */
    private LocalDateTime alterBegin;

    /**
     * 警戒线结束时间
     */
    private LocalDateTime alterEnd;

    /**
     * 警戒线批准人
     */
    private String alterApproveCode;

    /**
     * 警戒线批准原因
     */
    private LocalDateTime alterApproveDate;

    /**
     * 人民医院旧系统资格证书编号(导数据用)[襄阳暂存质控护士编码]
     */
    private String oldSiNo;

    /**
     * 患者医保审核确认标记
     */
    private String localExtFlag1;

    /**
     * 医保普通备注
     */
    private String localExtFlag2;

    /**
     * EMR住院流水号 SEQ_FIN_INPATIENTID
     */
    private String emrInpatientId;

    /**
     * 是否是单病种患者0：非单病种1：单病种
     */
    private Boolean isSingleDis;

    /**
     * 单病种ICD编码
     */
    private String singleDisId;

    /**
     * 定额结算手术名称
     */
    private String diseaseName;

    /**
     * 定额结算手术定额
     */
    private Double diseaseCost;

    /**
     * 材料费用
     */
    private String materialCost;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    private DeptOwnEnum deptOwn;

    /**
     * 急诊绿色通道结账
     */
    private Boolean isGreenBalance;

    /**
     * 跨省医保省编码
     */
    private String siProvinceCode;

    /**
     * 跨省医保省名称
     */
    private String siProvinceName;

    /**
     * 跨省医保市编码
     */
    private String siCityCode;

    /**
     * 跨省医保市名称
     */
    private String siCityName;

    /**
     * 医保特殊备注
     */
    private String localExtFlag3;

    /**
     * 是否是GCP患者，空值表示不是GCP患者，0取消GCP患者，1设置GCP患者。
     */
    private Boolean gcpInpatient;

    /**
     * 绿色通道标志 1是 0不是
     */
    private Boolean greenRoad;

    /**
     * VIP患者标志 1是 0不是
     */
    private Boolean vipFlag;

    /**
     * 是否是ERAS患者，空值表示不是ERAS患者，0取消ERAS患者，1设置ERAS患者。
     */
    private Boolean erasInpatient;

    /**
     * VTE等级
     */
    private String vte;

    /**
     * VTE等级是否被医生确认：0 未确认；1 已确认
     */
    private Boolean vteCfm;

    /**
     * 门诊号
     */
    private String clinicCode;

    /**
     * 住院预约发生序号
     */
    private Integer happenNo;

    /**
     * 医疗照顾人员类别
     */
    private String medicalStaff;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     */
    private String caseApproveFlag;

    /**
     *
     */
    private String caseReason;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIprInMainInfo) {
            var that = (FinIprInMainInfo) arg0;
            return StringUtils.equals(this.inpatientNo, that.inpatientNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(inpatientNo);
    }
}
