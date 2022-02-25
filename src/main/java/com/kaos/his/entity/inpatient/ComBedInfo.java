package com.kaos.his.entity.inpatient;

import java.util.Date;

import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.his.entity.common.DawnOrgEmpl;
import com.kaos.his.enums.impl.common.SexEnum;
import com.kaos.his.enums.impl.common.ValidStateEnum;
import com.kaos.his.enums.impl.inpatient.BedGradeEnum;
import com.kaos.his.enums.impl.inpatient.BedStateEnum;

/**
 * 床位信息（XYHIS.COM_BEDINFO）
 */
public class ComBedInfo {
    /**
     * 床号
     */
    public String bedNo = null;

    /**
     * 归属病区 {@link ComBedInfo.AssociateEntity#nurseCell}
     */
    public String nurseCellCode = null;

    /**
     * 床位等级编码
     */
    public BedGradeEnum feeGradeCode = null;

    /**
     * 编制代码
     */
    public String bedWeave = null;

    /**
     * 床位状态
     */
    public BedStateEnum bedState = null;

    /**
     * 病室号
     */
    public String wardNo = null;

    /**
     * 医师代码 {@link ComBedInfo.AssociateEntity#doc}
     */
    public String docCode = null;

    /**
     * 病床电话
     */
    public String bedPhoneCode = null;

    /**
     * 归属
     */
    public String ownerPc = null;

    /**
     * 医疗流水号 门诊是挂号单号 住院是住院流水号
     */
    public String clinicNo = null;

    /**
     * 出院日期(预约)
     */
    public Date prepayOutDate = null;

    /**
     * 有效标识
     */
    public ValidStateEnum validState = null;

    /**
     * 预约标志
     */
    public Boolean prepayFlag = null;

    /**
     * 顺序号
     */
    public Integer sortId = null;

    /**
     * 操作员 {@link ComBedInfo.AssociateEntity#operEmpl}
     */
    public String operCode = null;

    /**
     * 操作时间
     */
    public Date operDate = null;

    /**
     * 护理组
     */
    public String nurseTendGroup = null;

    /**
     * 住院医师编码 {@link ComBedInfo.AssociateEntity#houseDoc}
     */
    public String houseDocCode = null;

    /**
     * 主治医师编码 {@link ComBedInfo.AssociateEntity#chargeDoc}
     */
    public String chargeDocCode = null;

    /**
     * 主治医师编码 {@link ComBedInfo.AssociateEntity#chiefDoc}
     */
    public String chiefDocCode = null;

    /**
     * 主治医师编码 {@link ComBedInfo.AssociateEntity#dutyNurse}
     */
    public String dutyNurseCode = null;

    /**
     * 床位所属性别
     */
    public SexEnum bedSex = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 基本信息操作员 {@link ComBedInfo#nurseCellCode}
         */
        public DawnOrgDept nurseCell = null;

        /**
         * 医师 {@link ComBedInfo#docCode}
         */
        public DawnOrgEmpl doc = null;

        /**
         * 医师 {@link ComBedInfo#operCode}
         */
        public DawnOrgEmpl operEmpl = null;

        /**
         * 住院医师 {@link ComBedInfo#houseDocCode}
         */
        public DawnOrgEmpl houseDoc = null;

        /**
         * 住院医师 {@link ComBedInfo#chargeDocCode}
         */
        public DawnOrgEmpl chargeDoc = null;

        /**
         * 住院医师 {@link ComBedInfo#chiefDocCode}
         */
        public DawnOrgEmpl chiefDoc = null;

        /**
         * 住院医师 {@link ComBedInfo#dutyNurseCode}
         */
        public DawnOrgEmpl dutyNurse = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();

    /**
     * 获取去除病区的格式的床位号
     */
    public String getBriefBedNo() {
        return this.bedNo.replaceFirst("^" + this.nurseCellCode, "");
    }
}
