package com.kaos.skynet.api.data.cache.inpatient;

import com.kaos.skynet.api.data.entity.inpatient.FinSpecialCityPatient;
import com.kaos.skynet.api.data.mapper.inpatient.FinSpecialCityPatientMapper;
import com.kaos.skynet.core.type.Cache;

import com.kaos.skynet.core.type.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FinSpecialCityPatientCache extends Cache<String, FinSpecialCityPatient> {
    FinSpecialCityPatientCache(FinSpecialCityPatientMapper specialCityPatientMapper) {
        super(100, new Converter<String, FinSpecialCityPatient>() {
            @Override
            public FinSpecialCityPatient convert(String source) {
                return specialCityPatientMapper.querySpecialCityPatient(source);
            }
        });
    }
}
