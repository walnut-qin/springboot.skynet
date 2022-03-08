package com.kaos.skynet.api.mapper.inpatient.fee;

import java.util.List;

import com.kaos.skynet.entity.inpatient.fee.FinIpbInPrepay;

public interface FinIpbInPrepayMapper {
    /**
     * 查询预交金
     * 
     * @param inpatientNo 住院流水号, 等于 {@code null} 时，将IS NULL作为条件
     * @return
     */
    List<FinIpbInPrepay> queryPrepays(String inpatientNo);
}
