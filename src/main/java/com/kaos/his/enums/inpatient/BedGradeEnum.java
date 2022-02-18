package com.kaos.his.enums.inpatient;

import com.kaos.inf.IEnum;

/**
 * 床位等级 {@code COM_DICTIONARY#TYPE = BEDGRADE}
 */
public enum BedGradeEnum implements IEnum {
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

    /**
     * 构造
     * 
     * @param index
     * @param description
     */
    BedGradeEnum(String index, String description) {
        this.value = index;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
