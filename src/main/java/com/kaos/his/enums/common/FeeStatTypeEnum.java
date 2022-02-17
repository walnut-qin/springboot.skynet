package com.kaos.his.enums.common;

import com.kaos.inf.IEnum;

public enum FeeStatTypeEnum implements IEnum {
    病案首页("BA01", "病案首页"), 门诊发票("MZ01", "门诊发票"), 门诊统计("MZ02", "门诊统计"), 门诊日结报表("MZRJ", "门诊日结报表"), 住院发票("ZY01", "住院发票"),
    财务住院统计("ZY04", "财务住院统计"), 住院日结报表("ZYRJ", "住院日结报表"), 住院统计("ZYTJ", "住院统计");

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
    FeeStatTypeEnum(String index, String description) {
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
