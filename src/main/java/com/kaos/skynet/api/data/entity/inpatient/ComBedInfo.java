package com.kaos.skynet.api.data.entity.inpatient;

import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.enums.common.ValidStateEnum;
import com.kaos.skynet.api.enums.inpatient.BedGradeEnum;
import com.kaos.skynet.api.enums.inpatient.BedStateEnum;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 床位信息（XYHIS.COM_BEDINFO）
 */
@Getter
@Setter
@Builder
public class ComBedInfo {
    /**
     * 床号
     */
    private String bedNo;

    /**
     * 归属病区 {@link ComBedInfo.AssociateEntity#nurseCell}
     */
    private String nurseCellCode;

    /**
     * 床位等级编码
     */
    private BedGradeEnum feeGradeCode;

    /**
     * 编制代码
     */
    private String bedWeave;

    /**
     * 床位状态
     */
    private BedStateEnum bedState;

    /**
     * 病室号
     */
    private String wardNo;

    /**
     * 医师代码 {@link ComBedInfo.AssociateEntity#doc}
     */
    private String docCode;

    /**
     * 病床电话
     */
    private String bedPhoneCode;

    /**
     * 归属
     */
    private String ownerPc;

    /**
     * 医疗流水号 门诊是挂号单号 住院是住院流水号
     */
    private String clinicNo;

    /**
     * 出院日期(预约)
     */
    private Date prepayOutDate;

    /**
     * 有效标识
     */
    private ValidStateEnum validState;

    /**
     * 预约标志
     */
    private Boolean prepayFlag;

    /**
     * 顺序号
     */
    private Integer sortId;

    /**
     * 操作员 {@link ComBedInfo.AssociateEntity#operEmpl}
     */
    private String operCode;

    /**
     * 操作时间
     */
    private Date operDate;

    /**
     * 护理组
     */
    private String nurseTendGroup;

    /**
     * 住院医师编码 {@link ComBedInfo.AssociateEntity#houseDoc}
     */
    private String houseDocCode;

    /**
     * 主治医师编码 {@link ComBedInfo.AssociateEntity#chargeDoc}
     */
    private String chargeDocCode;

    /**
     * 主治医师编码 {@link ComBedInfo.AssociateEntity#chiefDoc}
     */
    private String chiefDocCode;

    /**
     * 主治医师编码 {@link ComBedInfo.AssociateEntity#dutyNurse}
     */
    private String dutyNurseCode;

    /**
     * 床位所属性别
     */
    private SexEnum bedSex;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof ComBedInfo) {
            var that = (ComBedInfo) arg0;
            return StringUtils.equals(this.bedNo, that.bedNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bedNo);
    }

    /**
     * 获取去除病区的格式的床位号
     */
    public String getBriefBedNo() {
        return this.bedNo.replaceFirst("^" + this.nurseCellCode, "");
    }
}
