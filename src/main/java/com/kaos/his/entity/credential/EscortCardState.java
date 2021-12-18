package com.kaos.his.entity.credential;

import java.util.Date;

import com.kaos.his.enums.EscortStateEnum;

/**
 * 陪护证状态记录
 */
public class EscortCardState {
    /**
     * 陪护证编号
     */
    public String escortNo = null;

    /**
     * 状态枚举
     */
    public EscortStateEnum state = null;

    /**
     * 登记时间
     */
    public Date operDate = null;

    /**
     * 备注
     */
    public String remark = null;
}
