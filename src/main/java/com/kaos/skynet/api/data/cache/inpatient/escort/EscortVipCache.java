package com.kaos.skynet.api.data.cache.inpatient.escort;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.inpatient.escort.EscortVip;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortVipMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;

@Component
public class EscortVipCache extends Cache<EscortVipCache.Key, EscortVip> {
    /**
     * 审核接口
     */
    @Autowired
    EscortVipMapper vipMapper;

    @Override
    @PostConstruct
    protected void postConstruct() {
        super.postConstruct(1000, new Converter<Key, EscortVip>() {
            @Override
            public EscortVip convert(Key source) {
                return vipMapper.queryEscortVip(source.cardNo, source.happenNo);
            }
        });
    }

    @Builder
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
