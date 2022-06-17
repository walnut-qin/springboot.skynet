package com.kaos.skynet.api.data.his.entity.inpatient;

import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.his.enums.SexEnum;
import com.kaos.skynet.api.data.his.enums.ValidEnum;
import com.kaos.skynet.core.type.Enum;

import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.AllArgsConstructor;
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
    private ValidEnum validState;

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

    /**
     * 床位等级 {@code COM_DICTIONARY#TYPE = BEDGRADE}
     */
    @Getter
    @AllArgsConstructor
    public enum BedGradeEnum implements Enum {
        单人间普("01", "单人间(普)"), 单人间标("02", "单人间(标)"), 单人间传("03", "单人间(传)"), 单人间精("04", "单人间(精)"), 双人间普("05", "双人间(普)"),
        双人间标("06", "双人间(标)"), 双人间传("07", "双人间(传)"), 双人间精("08", "双人间(精)"), 三人间普("09", "三人间(普)"), 三人间标("10", "三人间(标)"),
        三人间传("11", "三人间(传)"), 三人间精("12", "三人间(精)"), 四人间标("13", "四人间(标)"), 四人以上普("14", "四人以上普"), 四人以上传("15", "四人以上传"),
        四人以上精("16", "四人以上精"), 五人以上标("17", "五人以上标"), 层流洁净百("18", "层流洁净百"), 层流洁净千("19", "层流洁净千"), 层流洁净万("20", "层流洁净万"),
        监护病房床("21", "监护病房床"), 特殊防护床("22", "特殊防护床"), 急诊观察床("23", "急诊观察床"), 气垫床("24", "气垫床");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public enum BedStateEnum implements Enum {
        占床("O", "占床"), 空床("U", "空床"), 关闭("C", "关闭"), 挂床("H", "挂床"), 包床("W", "包床"), 污染("K", "污染"), 隔离("I", "隔离"),
        请假("R", "请假");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }
}
