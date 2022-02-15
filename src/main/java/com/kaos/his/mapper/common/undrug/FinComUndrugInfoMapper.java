package com.kaos.his.mapper.common.undrug;

import com.kaos.his.entity.common.undrug.FinComUndrugInfo;

public interface FinComUndrugInfoMapper {
    /**
     * 查询非药品信息
     * 
     * @param itemCode 非药品编码
     * @return
     */
    FinComUndrugInfo queryUndrugInfo(String itemCode);
}
