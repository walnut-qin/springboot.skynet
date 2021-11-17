package com.kaos.his.entity.personnel;

public class Patient extends Citizen {
    /**
     * 就诊卡号
     */
    public String cardNo;

    /**
     * 是否为GCP门诊患者标识
     */
    public boolean gcpOutPatient;
}
