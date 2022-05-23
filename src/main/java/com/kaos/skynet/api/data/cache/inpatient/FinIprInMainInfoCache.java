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

import lombok.Data;

/**
 * @param 类型 缓存
 * @param 映射 住院流水号 -> 住院信息
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class FinIprInMainInfoCache {
    /**
     * 数据库接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    @Component
    public class MasterCache extends Cache<String, FinIprInMainInfo> {
        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(String.class, 500, new Converter<String, FinIprInMainInfo>() {
                @Override
                public FinIprInMainInfo convert(String source) {
                    return inMainInfoMapper.queryInMainInfo(source);
                }
            });
        }
    }

    @Component
    public class SlaveCache extends Cache<SlaveCache.Key, List<FinIprInMainInfo>> {
        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(Key.class, 500, new Converter<Key, List<FinIprInMainInfo>>() {
                @Override
                public List<FinIprInMainInfo> convert(Key source) {
                    return inMainInfoMapper.queryInMainInfos(new FinIprInMainInfoMapper.Key() {
                        {
                            setCardNo(source.cardNo);
                            setHappenNo(source.happenNo);
                            setStates(source.states);
                        }
                    });
                }
            });
        }

        @Data
        public static class Key {
            /**
             * 卡号
             */
            private String cardNo = null;

            /**
             * 住院证序号
             */
            private Integer happenNo = null;

            /**
             * 在院状态
             */
            private List<InStateEnum> states = null;
        }
    }
}
