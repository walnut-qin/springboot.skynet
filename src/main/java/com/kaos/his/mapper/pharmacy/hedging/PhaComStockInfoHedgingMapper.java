package com.kaos.his.mapper.pharmacy.hedging;

import com.kaos.his.entity.pharmacy.hedging.PhaComStockInfoHedging;

public interface PhaComStockInfoHedgingMapper {
    /**
     * 主键查询
     * 
     * @param deptCode 科室编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param drugCode 药品编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    PhaComStockInfoHedging queryStockInfo(String deptCode, String drugCode);
}
