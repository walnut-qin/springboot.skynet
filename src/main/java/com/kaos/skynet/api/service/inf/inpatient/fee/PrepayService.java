package com.kaos.skynet.api.service.inf.inpatient.fee;

import java.util.Map;

import org.javatuples.Pair;

/**
 * 预交金业务
 */
public interface PrepayService {
    /**
     * 隔日召回后修改预交金
     * 
     * @param patientNo 住院号
     * @return {预交金序号, [修改前数值, 修改后数值]}
     */
    Map<Integer, Pair<Double, Double>> fixPrepayCost(String patientNo);
}