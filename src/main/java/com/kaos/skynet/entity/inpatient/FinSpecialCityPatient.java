package com.kaos.skynet.entity.inpatient;

public class FinSpecialCityPatient {
    /**
     * 住院流水号
     */
    public String patientNo = null;

    /**
     * 门诊医保患者标识 0 普通患者 1 门诊医保 2 川汇发热门诊患者
     */
    public String isSpecial = null;

    /**
     * 是否药物测试
     */
    public String isDrugTest = null;

    /**
     * 是否新冠
     */
    public String is2019NCov = null;
}
