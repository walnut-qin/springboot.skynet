package com.kaos.skynet.api.mapper.inpatient;

import com.kaos.skynet.entity.inpatient.ComBedInfo;

public interface ComBedInfoMapper {
    /**
     * 查询床位
     * 
     * @param bedNo 床位号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    ComBedInfo queryBedInfo(String bedNo);
}
