package com.kaos.skynet.api.data.his.cache.inpatient.escort.annex;

import com.kaos.skynet.api.data.his.entity.inpatient.escort.annex.EscortAnnexInfo;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.annex.EscortAnnexInfoMapper;
import com.kaos.skynet.core.type.Cache;

import com.kaos.skynet.core.type.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class EscortAnnexInfoCache extends Cache<String, EscortAnnexInfo> {
    EscortAnnexInfoCache(EscortAnnexInfoMapper annexInfoMapper) {
        super(1000, new Converter<String, EscortAnnexInfo>() {
            @Override
            public EscortAnnexInfo convert(String source) {
                return annexInfoMapper.queryAnnexInfo(source);
            }
        });
    }
}
