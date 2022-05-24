package com.kaos.skynet.api.data.cache.inpatient.escort.annex;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexCheck;
import com.kaos.skynet.api.data.mapper.inpatient.escort.annex.EscortAnnexCheckMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;

/**
 * @param 类型 缓存
 * @param 映射 患者卡号 -> 附件信息(附带审核信息)
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class EscortAnnexCheckCache {
    /**
     * 审核接口
     */
    @Autowired
    EscortAnnexCheckMapper annexCheckMapper;

    @Component
    public class MasterCache extends Cache<String, EscortAnnexCheck> {
        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(String.class, 100, new Converter<String, EscortAnnexCheck>() {
                @Override
                public EscortAnnexCheck convert(String source) {
                    return annexCheckMapper.queryAnnexCheck(source);
                }
            });
        }
    }

    @Component
    public class SlaveCache extends Cache<Key, List<EscortAnnexCheck>> {
        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(Key.class, 100, new Converter<Key, List<EscortAnnexCheck>>() {
                @Override
                public List<EscortAnnexCheck> convert(Key source) {
                    // 查询原始数据
                    var builder = EscortAnnexCheckMapper.Key.builder();
                    builder.cardNo(source.cardNo);
                    if (source.offset != null) {
                        builder.inspectBeginDate(LocalDateTime.now().plusDays(-source.offset))
                                .inspectEndDate(LocalDateTime.now());
                    }
                    var annexChecks = annexCheckMapper.queryAnnexChecks(builder.build());
                    // 时间逆序
                    annexChecks.sort((x, y) -> {
                        return y.getInspectDate().compareTo(x.getInspectDate());
                    });
                    return annexChecks;
                }
            });
        }
    }

    @Data
    @Builder
    public static class Key {
        /**
         * 就诊卡号
         */
        private String cardNo;

        /**
         * 有效期
         */
        private Integer offset;
    }
}
