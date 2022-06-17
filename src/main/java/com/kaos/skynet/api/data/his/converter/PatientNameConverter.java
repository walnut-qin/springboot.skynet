package com.kaos.skynet.api.data.his.converter;

import com.kaos.skynet.api.data.his.cache.DataCache;
import com.kaos.skynet.api.data.his.entity.common.ComPatientInfo;
import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientNameConverter implements Converter<String, String> {
    @Autowired
    DataCache dataCache;

    @Override
    public String convert(String cardNo) {
        // 判空
        if (cardNo == null) {
            return null;
        }

        // 执行逻辑
        ComPatientInfo patient = dataCache.getPatientInfoCache().get(cardNo);
        if (patient != null) {
            return patient.getName();
        }
        return cardNo;
    }
}
