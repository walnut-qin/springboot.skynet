package com.kaos.his.service.outpatient;

import java.util.Date;

public interface RegService {
    /**
     * 挂免费号
     * 
     * @param cardNo   就诊卡号
     * @param idenNo   身份证号
     * @param deptCode 看诊科室
     * @param docCode  看诊医生
     * @param seeDate  看诊时间
     */
    void freeRegister(String cardNo, String idenNo, String deptCode, String doctCode, Date seeDate);
}
