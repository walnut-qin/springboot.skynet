package com.kaos.skynet.api.data.his.cache.inpatient;

import com.kaos.skynet.api.data.his.entity.inpatient.FinIprPrepayIn;
import com.kaos.skynet.api.data.his.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.skynet.core.type.Cache;

import com.kaos.skynet.core.type.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Component
public class FinIprPrepayInCache extends Cache<FinIprPrepayInCache.Key, FinIprPrepayIn> {
    FinIprPrepayInCache(FinIprPrepayInMapper prepayInMapper) {
        super(100, new Converter<Key, FinIprPrepayIn>() {
            @Override
            public FinIprPrepayIn convert(Key source) {
                return prepayInMapper.queryPrepayIn(source.cardNo, source.happenNo);
            }
        });
    }

    /**
     * 索引
     */
    @Builder
    @EqualsAndHashCode
    public static class Key {
        /**
         * 卡号
         */
        private String cardNo;

        /**
         * 序号
         */
        private Integer happenNo;
    }
}
