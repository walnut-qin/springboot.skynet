package com.kaos.his.mapper.common.drug.hedging;

import com.kaos.his.entity.common.drug.hedging.PhaComStockInfoHedging;

public interface PhaComStockInfoHedgingMapper {
    /**
     * 主键查询
     * 
     * @param deptCode 科室编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param drugCode 药品编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    PhaComStockInfoHedging queryStockInfo(String deptCode, String drugCode);

    /**
     * 插入一条记录
     * 
     * @param phaComStockInfoHedging 插入药品对冲表库存表
     * @return
     */
    int insertStockInfo(PhaComStockInfoHedging phaComStockInfoHedging);

    /**
     * 修改记录
     * 
     * @param phaComStockInfoHedging 主键作索引，主键值为 {@code null} 时，将 IS NULL作为判断条件
     * @return
     */
    int updateStockInfo(PhaComStockInfoHedging phaComStockInfoHedging);
}
