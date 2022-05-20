package com.kaos.skynet.api.data.cache.outpatient;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.outpatient.FinOprRegister;
import com.kaos.skynet.api.data.mapper.outpatient.FinOprRegisterMapper;
import com.kaos.skynet.api.enums.common.TransTypeEnum;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public class FinOprRegisterCache extends Cache<FinOprRegisterCache.Key, FinOprRegister> {
    @Autowired
    FinOprRegisterMapper registerMapper;

    @Override
    @PostConstruct
    protected void postConstruct() {
        super.postConstruct(Key.class, 100, new Converter<Key, FinOprRegister>() {
            @Override
            public FinOprRegister convert(Key source) {
                return registerMapper.queryRegister(source.clinicCode, source.transType);
            }
        });
    }

    @Data
    public static class Key {
        /**
         * 门诊号
         */
        private String clinicCode = null;

        /**
         * 交易类型
         */
        private TransTypeEnum transType = null;
    }
}
