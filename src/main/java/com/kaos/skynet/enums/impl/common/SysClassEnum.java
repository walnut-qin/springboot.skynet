package com.kaos.skynet.enums.impl.common;

import com.kaos.skynet.core.type.Enum;

/**
 * 门诊系统类别，维护在COM_DICTIONARY的MZEXECBILL类型
 */
public enum SysClassEnum implements Enum {
    西药("P", "西药"), 中成药("PCZ", "中成药"), 中草药("PCC", "中草药"), 治疗("UZ", "治疗"), 检查("UC", "检查"), 手术("UO", "手术"), 检验("UL", "检验"),
    非药品("U", "非药品"), 其他("UT", "其他");

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
    SysClassEnum(String value, String description) {
        this.value = value;
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
