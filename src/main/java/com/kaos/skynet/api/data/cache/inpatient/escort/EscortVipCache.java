package com.kaos.skynet.api.data.cache.inpatient.escort;

import com.kaos.skynet.api.data.entity.inpatient.escort.EscortVip;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortVipMapper;
import com.kaos.skynet.core.type.Cache;

import com.kaos.skynet.core.type.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Component
public class EscortVipCache extends Cache<EscortVipCache.Key, EscortVip> {
    EscortVipCache(EscortVipMapper vipMapper) {
        super(1000, new Converter<Key, EscortVip>() {
            @Override
            public EscortVip convert(Key source) {
                return vipMapper.queryEscortVip(source.cardNo, source.happenNo);
            }
        });
    }

    @Builder
    @EqualsAndHashCode
    public static class Key {
        /**
         * 卡号
         */
        private String cardNo;

        /**
         * 住院证序号
         */
        private Integer happenNo;
    }
}
