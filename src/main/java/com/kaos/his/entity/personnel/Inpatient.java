package com.kaos.his.entity.personnel;

import com.kaos.his.enums.InpatientStateEnum;

public class Inpatient extends Deptpatient {
    /**
     * 住院号
     */
    public String patientNo;

    /**
     * 在院状态
     */
    public InpatientStateEnum state;
}
