package com.kaos.his.entity.personnel;

public class Patient extends Citizen {
    /**
     * 就诊卡号
     */
    public String cardNo = null;

    /**
     * 是否为GCP门诊患者标识
     */
    public Boolean gcpOutPatient = null;
}
