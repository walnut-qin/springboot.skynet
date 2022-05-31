package com.kaos.skynet.api.data.converter;

import com.kaos.skynet.api.data.cache.DataCache;
import com.kaos.skynet.api.data.entity.inpatient.ComBedInfo;
import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 床号到简略床位号的转换器
 */
@Component
public class BedNoConverter extends Converter<String, String> {
    @Autowired
    DataCache dataCache;

    @Override
    public String convert(String bedNo) {
        ComBedInfo bedInfo = dataCache.getBedInfoCache().get(bedNo);
        if (bedInfo != null) {
            return bedInfo.getBriefBedNo();
        }
        return bedNo;
    }
}
