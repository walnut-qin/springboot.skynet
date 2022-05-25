package com.kaos.skynet.api.data.cache.inpatient.escort;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EscortMainInfoCache extends Cache<String, EscortMainInfo> {
    @Autowired
    EscortMainInfoMapper mainInfoMapper;

    @Override
    @PostConstruct
    protected void postConstruct() {
        super.postConstruct(3000, new Converter<String, EscortMainInfo>() {
            @Override
            public EscortMainInfo convert(String source) {
                return mainInfoMapper.queryEscortMainInfo(source);
            }
        });
    }
}
