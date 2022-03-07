package com.kaos.skynet.service.inf.outpatient;

import java.util.Date;

import com.kaos.skynet.enums.impl.common.PayModeEnum;

public interface RegisterService {
    /**
     * 挂免费号
     * 
     * @param cardNo   就诊卡号
     * @param idenNo   身份证号
     * @param deptCode 看诊科室
     * @param docCode  看诊医生
     * @param seeDate  看诊时间
     * @param noon     午别
     * @param operCode 挂号操作员
     * @param payMode  支付方式
     */
    void freeRegister(String cardNo, String deptCode, String docCode, Date seeDate, String operCode,
            PayModeEnum payMode);
}
