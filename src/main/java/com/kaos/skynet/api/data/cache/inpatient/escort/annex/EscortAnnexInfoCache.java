package com.kaos.skynet.api.data.cache.inpatient.escort.annex;

import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexInfo;
import com.kaos.skynet.api.data.mapper.inpatient.escort.annex.EscortAnnexInfoMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
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
