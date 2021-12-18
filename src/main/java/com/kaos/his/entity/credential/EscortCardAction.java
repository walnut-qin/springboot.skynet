package com.kaos.his.entity.credential;

import java.util.Date;

import com.kaos.his.enums.EscortActionEnum;

/**
 * 陪护出入记录
 */
public class EscortCardAction {
    /**
     * 陪护证编号
     */
    public String escortNo = null;

    /**
     * 出入动作枚举
     */
    public EscortActionEnum action = null;

    /**
     * 登记时间
     */
    public Date operDate = null;

    /**
     * 备注
     */
    public String remark = null;
}
