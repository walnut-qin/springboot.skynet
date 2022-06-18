package com.kaos.skynet.api.data.his.router;

import com.kaos.skynet.api.data.his.cache.DataCache;
import com.kaos.skynet.api.data.his.entity.inpatient.ComBedInfo;
import com.kaos.skynet.core.type.Router;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 床号到简略床位号的转换器
 */
@Component
public class BedNoRouter implements Router<String, String> {
    @Autowired
    DataCache dataCache;

    @Override
    public String route(String bedNo) {
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
