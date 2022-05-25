package com.kaos.skynet.api.data.cache.inpatient.escort.annex;

import java.util.List;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexInfo;
import com.kaos.skynet.api.data.mapper.inpatient.escort.annex.EscortAnnexInfoMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;

@Getter
@Component
public class EscortAnnexInfoCache {
    @Autowired
    MasterCache masterCache;

    @Autowired
    SlaveCache slaveCache;

    @Component
    public static class MasterCache extends Cache<String, EscortAnnexInfo> {
        @Autowired
        EscortAnnexInfoMapper annexInfoMapper;

        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(1000, new Converter<String, EscortAnnexInfo>() {
                @Override
                public EscortAnnexInfo convert(String source) {
                    return annexInfoMapper.queryAnnexInfo(source);
                }
            });
        }
    }

    @Component
    public static class SlaveCache extends Cache<SlaveCache.Key, List<EscortAnnexInfo>> {
        @Autowired
        EscortAnnexInfoMapper annexInfoMapper;

        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(100, new Converter<Key, List<EscortAnnexInfo>>() {
                @Override
                public List<EscortAnnexInfo> convert(Key source) {
                    return annexInfoMapper.queryAnnexInfos(EscortAnnexInfoMapper.Key.builder()
                            .cardNo(source.cardNo).checked(source.checked).build());
                }
            });
        }

        @Builder
        public static class Key {
            /**
             * 就诊卡号
             */
            private String cardNo;

            /**
             * 是否已审核
             */
            private Boolean checked;
        }
    }
}
