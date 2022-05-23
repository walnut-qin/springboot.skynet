package com.kaos.skynet.api.data.cache.inpatient.escort.annex;

import java.util.List;

import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexInfo;
import com.kaos.skynet.api.data.mapper.inpatient.escort.annex.EscortAnnexInfoMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public class EscortAnnexInfoCache {
    @Autowired
    EscortAnnexInfoMapper annexInfoMapper;

    @Component
    public class MasterCache extends Cache<String, EscortAnnexInfo> {
        @Override
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
    public class SlaveCache extends Cache<SlaveCache.Key, List<EscortAnnexInfo>> {
        @Override
        protected void postConstruct() {
            super.postConstruct(Key.class, 100, new Converter<Key, List<EscortAnnexInfo>>() {
                @Override
                public List<EscortAnnexInfo> convert(Key source) {
                    return annexInfoMapper.queryAnnexInfos(new EscortAnnexInfoMapper.Key() {
                        {
                            setCardNo(source.getCardNo());
                            setChecked(source.getChecked());
                        }
                    });
                }
            });
        }

        @Data
        public static class Key {
            /**
             * 就诊卡号
             */
            private String cardNo = null;

            /**
             * 是否已审核
             */
            private Boolean checked = null;
        }
    }
}
