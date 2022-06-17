package com.kaos.skynet.api.data.his.cache.inpatient.escort;

import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.core.type.Cache;

import com.kaos.skynet.core.type.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EscortMainInfoCache extends Cache<String, EscortMainInfo> {
    EscortMainInfoCache(EscortMainInfoMapper mainInfoMapper) {
        super(3000, new Converter<String, EscortMainInfo>() {
            @Override
            public EscortMainInfo convert(String source) {
                return mainInfoMapper.queryEscortMainInfo(source);
            }
        });
    }
}
