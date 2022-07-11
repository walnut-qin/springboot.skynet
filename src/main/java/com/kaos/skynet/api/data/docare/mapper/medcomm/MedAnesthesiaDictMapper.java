package com.kaos.skynet.api.data.docare.mapper.medcomm;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kaos.skynet.api.data.docare.entity.medcomm.MedAnesthesiaDict;

@DS("docare")
public interface MedAnesthesiaDictMapper {
    /**
     * 主键检索
     * 
     * @param anesName
     * @return
     */
    MedAnesthesiaDict queryAnesthesiaDict(String anesName);
}
