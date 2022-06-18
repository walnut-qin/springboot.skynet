package com.kaos.skynet.api.data.his.cache.inpatient.escort.annex;

import com.kaos.skynet.api.data.his.entity.inpatient.escort.annex.EscortAnnexCheck;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.annex.EscortAnnexCheckMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * @param 类型 缓存
 * @param 映射 患者卡号 -> 附件信息(附带审核信息)
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Getter
@Component
public class EscortAnnexCheckCache extends Cache<String, EscortAnnexCheck> {
    EscortAnnexCheckCache(EscortAnnexCheckMapper annexCheckMapper) {
        super(1000, new Converter<String, EscortAnnexCheck>() {
            @Override
            public EscortAnnexCheck convert(String source) {
                return annexCheckMapper.queryAnnexCheck(source);
            }
        });
    }
}
