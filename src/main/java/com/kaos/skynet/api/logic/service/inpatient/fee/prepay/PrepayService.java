package com.kaos.skynet.api.logic.service.inpatient.fee.prepay;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

/**
 * 预交金服务
 */
@Log4j
@Service
public class PrepayService {
    /**
     * 隔日召回后修改预交金
     * 
     * @param patientNo 住院号
     * @return
     */
    public List<PrepayModifyResult> fixRecallPrepay(String patientNo) {
        return null;
    }

    /**
     * 预交金修改结果
     */
    @Getter
    public static class PrepayModifyResult {
        /**
         * 住院流水号
         */
        private String inPatientNo;

        /**
         * 预交金序号
         */
        private Integer happenNo;

        /**
         * 原值
         */
        private Double oldCost;

        /**
         * 新值
         */
        private Double newCost;
    }
}
