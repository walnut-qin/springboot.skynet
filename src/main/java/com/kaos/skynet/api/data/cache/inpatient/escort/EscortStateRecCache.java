package com.kaos.skynet.api.data.cache.inpatient.escort;

import java.util.List;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 患者卡号 -> 附件信息(附带审核信息)
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class EscortStateRecCache extends Cache<String, List<EscortStateRec>> {
    /**
     * 审核接口
     */
    @Autowired
    EscortStateRecMapper stateRecMapper;

    @Override
    @PostConstruct
    protected void postConstruct() {
        super.postConstruct(String.class, 100, new Converter<String, List<EscortStateRec>>() {
            @Override
            public List<EscortStateRec> convert(String source) {
                // 检索原始数据
                List<EscortStateRec> stateRecs = stateRecMapper.queryEscortStateRecs(source);
                // 按recNo逆序
                stateRecs.sort((x, y) -> {
                    return y.getRecNo().compareTo(x.getRecNo());
                });
                return stateRecs;
            }
        });
    }
}
