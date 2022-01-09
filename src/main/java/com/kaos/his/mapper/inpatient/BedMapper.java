package com.kaos.his.mapper.inpatient;

import com.kaos.his.entity.inpatient.Bed;
import com.kaos.his.enums.ValidStateEnum;

public interface BedMapper {
    /**
     * 查询床位
     * 
     * @param bedNo
     * @return
     */
    Bed queryBed(String bedNo, ValidStateEnum valid);
}
