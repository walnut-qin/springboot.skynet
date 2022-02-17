package com.kaos.his.mapper.common;

import com.kaos.his.entity.common.FinComFeeCodeStat;
import com.kaos.his.enums.common.FeeStatTypeEnum;
import com.kaos.his.enums.common.MinFeeEnum;

public interface FinComFeeCodeStatMapper {
    /**
     * 查询对照记录
     * 
     * @param reportType 报表类型, 值为 {@code null} 时，将 IS NULL 作为判断条件
     * @param feeCode    最小类别, 值为 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinComFeeCodeStat queryFeeCodeStat(FeeStatTypeEnum reportType, MinFeeEnum feeCode);
}
