package com.kaos.his.service.outpatient;

import java.util.List;

/**
 * 划价收费类业务
 */
public interface FeeService {
    /**
     * 门诊非药品划价
     * 
     * @param clinicCode 门诊号
     * @param operCode   划价员
     * @param itemCodes  项目编码列表
     * @param termNo     非药品术语号
     */
    void undrugPriced(String clinicCode, String operCode, List<String> itemCodes, String termNo);
}
