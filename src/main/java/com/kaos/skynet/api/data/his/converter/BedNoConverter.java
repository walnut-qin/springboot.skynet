package com.kaos.skynet.api.data.his.converter;

import com.kaos.skynet.api.data.his.cache.DataCache;
import com.kaos.skynet.api.data.his.entity.inpatient.ComBedInfo;
import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 床号到简略床位号的转换器
 */
@Component
public class BedNoConverter implements Converter<String, String> {
    @Autowired
    DataCache dataCache;

    @Override
    public String convert(String bedNo) {
        // 判空
        if (bedNo == null) {
            return null;
        }

        // 执行逻辑
        ComBedInfo bedInfo = dataCache.getBedInfoCache().get(bedNo);
        if (bedInfo != null) {
            return bedInfo.getBriefBedNo();
        }
        return bedNo;
    }
}
