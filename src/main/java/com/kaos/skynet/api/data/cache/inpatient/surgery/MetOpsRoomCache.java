package com.kaos.skynet.api.data.cache.inpatient.surgery;

import com.kaos.skynet.api.data.entity.inpatient.surgery.MetOpsRoom;
import com.kaos.skynet.api.data.mapper.inpatient.surgery.MetOpsRoomMapper;
import com.kaos.skynet.core.type.Cache;
import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 手术间编码 -> 手术间信息
 * @param 容量 100
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class MetOpsRoomCache extends Cache<String, MetOpsRoom> {
    MetOpsRoomCache(MetOpsRoomMapper metOpsRoomMapper) {
        super(100, new Converter<String,MetOpsRoom>() {
            @Override
            public MetOpsRoom convert(String key) {
                return metOpsRoomMapper.queryMetOpsRoom(key);
            }
        });
    }
}
