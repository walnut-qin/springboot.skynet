package com.kaos.his.entity.personnel;

import com.kaos.his.enums.OutpatientStateEnum;

/**
 * 门诊患者
 */
public class Outpatient extends Deptpatient {
    /**
     * 门诊号
     */
    public String clinicCode;

    /**
     * 门诊患者状态
     */
    public OutpatientStateEnum state;
}
