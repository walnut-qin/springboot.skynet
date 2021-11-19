package com.kaos.his.entity.personnel;

import java.sql.Date;

import com.kaos.his.entity.credential.HospitalizationCertificate;
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

    /**
     * 病区编码
     */
    public String nurseCellCode;

    /**
     * 床号
     */
    public String bedNo;

    /**
     * 入院日期
     */
    public Date inDate;

    /**
     * 出院日期
     */
    public Date outDate;

    /**
     * 住院证实体
     */
    public HospitalizationCertificate hospitalizationCertificate;
}
