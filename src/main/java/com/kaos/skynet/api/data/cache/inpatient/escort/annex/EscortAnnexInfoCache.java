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
import lombok.Data;

@Component
public class EscortAnnexInfoCache {
    @Autowired
    EscortAnnexInfoMapper annexInfoMapper;

    @Component
    public class MasterCache extends Cache<String, EscortAnnexInfo> {
        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(String.class, 100, new Converter<String, EscortAnnexInfo>() {
                @Override
                public EscortAnnexInfo convert(String source) {
                    return annexInfoMapper.queryAnnexInfo(source);
                }
            });
        }
    }

    @Component
    public class SlaveCache extends Cache<SlaveCacheKey, List<EscortAnnexInfo>> {
        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(SlaveCacheKey.class, 100, new Converter<SlaveCacheKey, List<EscortAnnexInfo>>() {
                @Override
                public List<EscortAnnexInfo> convert(SlaveCacheKey source) {
                    return annexInfoMapper.queryAnnexInfos(EscortAnnexInfoMapper.Key.builder()
                            .cardNo(source.getCardNo()).checked(source.getChecked()).build());
                }
            });
        }
    }

    @Data
    @Builder
    public static class SlaveCacheKey {
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
