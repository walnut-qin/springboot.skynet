package com.kaos.skynet.api.data.cache.common;

import com.kaos.skynet.api.data.entity.common.ComPatientInfo;
import com.kaos.skynet.api.data.mapper.common.ComPatientInfoMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 患者信息中包含登记的内容，存在时效性，该时效设置为1分钟
 * 
 * @param 类型 缓存
 * @param 映射 患者卡号 -> 患者信息
 * @param 容量 300
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class ComPatientInfoCache extends Cache<String, ComPatientInfo> {
    /**
     * 数据库接口
     */
    ComPatientInfoCache(ComPatientInfoMapper patientInfoMapper) {
        super(300, new Converter<String, ComPatientInfo>() {
            @Override
            public ComPatientInfo convert(String source) {
                return patientInfoMapper.queryPatientInfo(source);
            }
        });
    }
}
