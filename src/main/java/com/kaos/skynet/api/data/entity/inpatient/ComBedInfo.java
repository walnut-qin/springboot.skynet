package com.kaos.skynet.api.data.entity.inpatient;

import java.util.Date;

import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.enums.common.ValidStateEnum;
import com.kaos.skynet.api.enums.inpatient.BedGradeEnum;
import com.kaos.skynet.api.enums.inpatient.BedStateEnum;

import lombok.Data;

/**
 * 床位信息（XYHIS.COM_BEDINFO）
 */
@Data
public class ComBedInfo {
    /**
     * 床号
     */
    private String bedNo = null;

    /**
     * 归属病区 {@link ComBedInfo.AssociateEntity#nurseCell}
     */
    private String nurseCellCode = null;

    /**
     * 床位等级编码
     */
    private BedGradeEnum feeGradeCode = null;

    /**
     * 编制代码
     */
    private String bedWeave = null;

    /**
     * 床位状态
     */
    private BedStateEnum bedState = null;

    /**
     * 病室号
     */
    private String wardNo = null;

    /**
     * 医师代码 {@link ComBedInfo.AssociateEntity#doc}
     */
    private String docCode = null;

    /**
     * 病床电话
     */
    private String bedPhoneCode = null;

    /**
     * 归属
     */
    private String ownerPc = null;

    /**
     * 医疗流水号 门诊是挂号单号 住院是住院流水号
     */
    private String clinicNo = null;

    /**
     * 出院日期(预约)
     */
    private Date prepayOutDate = null;

    /**
     * 有效标识
     */
    private ValidStateEnum validState = null;

    /**
     * 预约标志
     */
    private Boolean prepayFlag = null;

    /**
     * 顺序号
     */
    private Integer sortId = null;

    /**
     * 操作员 {@link ComBedInfo.AssociateEntity#operEmpl}
     */
    private String operCode = null;

    /**
     * 操作时间
     */
    private Date operDate = null;

    /**
     * 护理组
     */
    private String nurseTendGroup = null;

    /**
     * 住院医师编码 {@link ComBedInfo.AssociateEntity#houseDoc}
     */
    private String houseDocCode = null;

    /**
     * 主治医师编码 {@link ComBedInfo.AssociateEntity#chargeDoc}
     */
    private String chargeDocCode = null;

    /**
     * 主治医师编码 {@link ComBedInfo.AssociateEntity#chiefDoc}
     */
    private String chiefDocCode = null;

    /**
     * 主治医师编码 {@link ComBedInfo.AssociateEntity#dutyNurse}
     */
    private String dutyNurseCode = null;

    /**
     * 床位所属性别
     */
    private SexEnum bedSex = null;

    /**
     * 获取去除病区的格式的床位号
     */
    public String getBriefBedNo() {
        return this.bedNo.replaceFirst("^" + this.nurseCellCode, "");
    }
}
