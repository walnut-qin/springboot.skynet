package com.kaos.skynet.api.data.cache.inpatient;

import java.util.List;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.enums.inpatient.InStateEnum;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;

/**
 * @param 类型 缓存
 * @param 映射 住院流水号 -> 住院信息
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Getter
@Component
public class FinIprInMainInfoCache {
    @Autowired
    MasterCache masterCache;

    @Autowired
    SlaveCache slaveCache;

    @Component
    public static class MasterCache extends Cache<String, FinIprInMainInfo> {
        @Autowired
        FinIprInMainInfoMapper inMainInfoMapper;

        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(500, new Converter<String, FinIprInMainInfo>() {
                @Override
                public FinIprInMainInfo convert(String source) {
                    return inMainInfoMapper.queryInMainInfo(source);
                }
            });
        }
    }

    @Component
    public static class SlaveCache extends Cache<SlaveCache.Key, List<FinIprInMainInfo>> {
        @Autowired
        FinIprInMainInfoMapper inMainInfoMapper;

        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(500, new Converter<Key, List<FinIprInMainInfo>>() {
                @Override
                public List<FinIprInMainInfo> convert(Key source) {
                    return inMainInfoMapper.queryInMainInfos(FinIprInMainInfoMapper.Key.builder()
                            .cardNo(source.cardNo).happenNo(source.happenNo).states(source.states).build());
                }
            });
        }

        @Builder
        public static class Key {
            /**
             * 卡号
             */
            private String cardNo;

            /**
             * 住院证序号
             */
            private Integer happenNo;

            /**
             * 在院状态
             */
            private List<InStateEnum> states;
        }
    }
}
