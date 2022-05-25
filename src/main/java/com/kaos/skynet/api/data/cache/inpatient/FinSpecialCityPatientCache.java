package com.kaos.skynet.api.data.cache.inpatient;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.inpatient.FinSpecialCityPatient;
import com.kaos.skynet.api.data.mapper.inpatient.FinSpecialCityPatientMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FinSpecialCityPatientCache extends Cache<String, FinSpecialCityPatient> {
    @Autowired
    FinSpecialCityPatientMapper specialCityPatientMapper;

    @Override
    @PostConstruct
    protected void postConstruct() {
        super.postConstruct(100, new Converter<String, FinSpecialCityPatient>() {
            @Override
            public FinSpecialCityPatient convert(String source) {
                return specialCityPatientMapper.querySpecialCityPatient(source);
            }
        });
    }
}
