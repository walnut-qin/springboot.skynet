package com.kaos.skynet.api.mapper.common;

import com.kaos.skynet.api.entity.common.ComPatientInfo;

public interface ComPatientInfoMapper {
    /**
     * 主键查询
     * 
     * @param cardNo 就诊卡号；值为 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    ComPatientInfo queryPatientInfo(String cardNo);
}
