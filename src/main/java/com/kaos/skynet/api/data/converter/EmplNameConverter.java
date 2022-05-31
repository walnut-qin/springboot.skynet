package com.kaos.skynet.api.data.converter;

import com.kaos.skynet.api.data.cache.DataCache;
import com.kaos.skynet.api.data.entity.common.DawnOrgEmpl;
import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmplNameConverter extends Converter<String, String> {
    @Autowired
    DataCache dataCache;

    @Override
    public String convert(String emplCode) {
        DawnOrgEmpl empl = dataCache.getEmplCache().get(emplCode);
        if (empl != null) {
            return empl.getEmplName();
        }
        return emplCode;
    }
}
