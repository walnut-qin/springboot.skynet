package com.kaos.skynet.api.data.his.cache.inpatient;

import com.kaos.skynet.api.data.his.entity.inpatient.ComBedInfo;
import com.kaos.skynet.api.data.his.mapper.inpatient.ComBedInfoMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 床位编码 -> 床位信息
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class ComBedInfoCache extends Cache<String, ComBedInfo> {
    ComBedInfoCache(ComBedInfoMapper bedInfoMapper) {
        super(500, new Converter<String, ComBedInfo>() {
            @Override
            public ComBedInfo convert(String source) {
                return bedInfoMapper.queryBedInfo(source);
            }
        });
    }
}
