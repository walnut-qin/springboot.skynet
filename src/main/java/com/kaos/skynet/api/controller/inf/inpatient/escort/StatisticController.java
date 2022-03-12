package com.kaos.skynet.api.controller.inf.inpatient.escort;

public interface StatisticController {
    /**
     * 入院时间
     */
    public String inDate = null;

    /**
     * 床号
     */
    public String bedNo = null;

    /**
     * 姓名
     */
    public String name = null;

    /**
     * 就诊卡号
     */
    public String cardNo = null;

    /**
     * 核酸结果
     */
    public String nucleicAcidResult = null;

    /**
     * 健康码
     */
    public String healthCode = null;

    /**
     * 行程码
     */
    public String travelCode = null;

    /**
     * 14天内是否去过高风险地区
     */
    public String highRiskFlag = null;

    /**
     * 到过高风险地区清单
     */
    public String highRiskArea = null;
}
