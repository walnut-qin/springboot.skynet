package com.kaos.skynet.api.data.his.cache.inpatient;

import com.kaos.skynet.api.data.his.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.his.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.core.type.Cache;

import com.kaos.skynet.core.type.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 住院流水号 -> 住院信息
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class FinIprInMainInfoCache extends Cache<String, FinIprInMainInfo> {
    FinIprInMainInfoCache(FinIprInMainInfoMapper inMainInfoMapper) {
        super(500, new Converter<String, FinIprInMainInfo>() {
            @Override
            public FinIprInMainInfo convert(String source) {
                return inMainInfoMapper.queryInMainInfo(source);
            }
        });
    }
}
