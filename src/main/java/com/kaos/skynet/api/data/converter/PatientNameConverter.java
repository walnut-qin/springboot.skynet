package com.kaos.skynet.api.data.converter;

import com.kaos.skynet.api.data.cache.DataCache;
import com.kaos.skynet.api.data.entity.common.ComPatientInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PatientNameConverter implements Converter<String, String> {
    @Autowired
    DataCache dataCache;

    @Override
    public String convert(String cardNo) {
        ComPatientInfo patient = dataCache.getPatientInfoCache().get(cardNo);
        if (patient != null) {
            return patient.getName();
        }
        return cardNo;
    }
}
