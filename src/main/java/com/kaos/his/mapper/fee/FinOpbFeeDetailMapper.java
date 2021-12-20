package com.kaos.his.mapper.fee;

import com.kaos.his.entity.fee.FinOpbFeeDetail;
import com.kaos.his.enums.TransTypeEnum;

import org.springframework.stereotype.Repository;

@Repository
public interface FinOpbFeeDetailMapper {
    /**
     * 主键查询
     * 
     * @param recipeNo
     * @param seqNo
     * @param transType
     * @return
     */
    FinOpbFeeDetail QueryFinOpbFeeDetail(String recipeNo, Integer seqNo, TransTypeEnum transType);

    /**
     * 查询最近一条划价记录
     * @param cardNo
     * @return
     */
    FinOpbFeeDetail QueryLastFinOpbFeeDetail(String cardNo, String itemCode);
}
