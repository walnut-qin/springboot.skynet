package com.kaos.skynet.api.data.cache.inpatient;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.inpatient.FinIprPrepayIn;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public class FinIprPrepayInCache extends Cache<FinIprPrepayInCache.Key, FinIprPrepayIn> {
    @Autowired
    FinIprPrepayInMapper prepayInMapper;

    @Override
    @PostConstruct
    protected void postConstruct() {
        super.postConstruct(Key.class, 100, new Converter<Key, FinIprPrepayIn>() {
            @Override
            public FinIprPrepayIn convert(Key source) {
                return prepayInMapper.queryPrepayIn(source.cardNo, source.happenNo);
            }
        });
    }

    /**
     * 索引
     */
    @Data
    public static class Key {
        /**
         * 卡号
         */
        private String cardNo = null;

        /**
         * 序号
         */
        private Integer happenNo = null;
    }
}
