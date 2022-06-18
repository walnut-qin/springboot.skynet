package com.kaos.skynet.api.data.his.router;

import com.kaos.skynet.api.data.his.cache.DataCache;
import com.kaos.skynet.api.data.his.entity.common.DawnOrgEmpl;
import com.kaos.skynet.core.type.Router;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class EmplNameRouter implements Router<String, String> {
    @Autowired
    DataCache dataCache;

    @Override
    public String route(String emplCode) {
        // 判空
        if (emplCode == null) {
            return null;
        }

        // 执行逻辑
        DawnOrgEmpl empl = dataCache.getEmplCache().get(emplCode);
        if (empl != null) {
            return empl.getEmplName();
        }
        return emplCode;
    }
}
