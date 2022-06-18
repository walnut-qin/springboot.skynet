package com.kaos.skynet.api.data.his.cache.common;

import com.kaos.skynet.api.data.his.entity.common.MetOpsnWyDoc;
import com.kaos.skynet.api.data.his.mapper.common.MetOpsnWyDocMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MetOpsnWyDocCache extends Cache<String, MetOpsnWyDoc> {
    MetOpsnWyDocCache(MetOpsnWyDocMapper metOpsnWyDocMapper) {
        super(100, new Converter<String, MetOpsnWyDoc>() {
            @Override
            public MetOpsnWyDoc convert(String source) {
                return metOpsnWyDocMapper.queryEmpl(source);
            }
        });
    }
}