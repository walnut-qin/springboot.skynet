package com.kaos.his.mapper.inpatient;

import com.kaos.his.entity.inpatient.ComBedInfo;

public interface ComBedInfoMapper {
    /**
     * 查询床位
     * 
     * @param bedNo
     * @return
     */
    ComBedInfo queryBedInfo(String bedNo);
}
