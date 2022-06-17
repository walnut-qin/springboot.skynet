package com.kaos.skynet.api.data.his.entity.inpatient;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FinSpecialCityPatient {
    /**
     * 住院流水号
     */
    private String patientNo;

    /**
     * 门诊医保患者标识 0 普通患者 1 门诊医保 2 川汇发热门诊患者
     */
    private String isSpecial;

    /**
     * 是否药物测试
     */
    private String isDrugTest;

    /**
     * 是否新冠
     */
    private String is2019NCov;
}
