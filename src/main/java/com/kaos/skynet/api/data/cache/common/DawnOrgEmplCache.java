package com.kaos.skynet.api.data.cache.common;

import com.kaos.skynet.api.data.entity.common.DawnOrgEmpl;
import com.kaos.skynet.api.data.mapper.common.DawnOrgEmplMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 职工编码 -> 职工信息
 * @param 容量 1000
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class DawnOrgEmplCache extends Cache<String, DawnOrgEmpl> {
    /**
     * 数据库接口
     */
    DawnOrgEmplCache(DawnOrgEmplMapper emplMapper) {
        super(1000, new Converter<String, DawnOrgEmpl>() {
            @Override
            public DawnOrgEmpl convert(String source) {
                return emplMapper.queryEmpl(source);
            }
        });
    }
}
