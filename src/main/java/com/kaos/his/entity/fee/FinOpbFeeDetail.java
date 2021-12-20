package com.kaos.his.entity.fee;

import java.util.Date;

import com.kaos.his.enums.TransTypeEnum;

public class FinOpbFeeDetail {
    /**
     * 处方号
     */
    public String recipeNo = null;

    /**
     * 处方内流水号
     */
    public Integer seqNo = null;

    /**
     * 交易类型
     */
    public TransTypeEnum transType = null;

    /**
     * 患者卡号
     */
    public String cardNo = null;

    /**
     * 操作员
     */
    public String operCode = null;

    /**
     * 操作时间
     */
    public Date operDate = null;

    /**
     * 项目编码
     */
    public String itemCode = null;

    /**
     * 项目名称
     */
    public String itemName = null;
}
