package com.kaos.skynet.api.data.cache.pipe.lis;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.pipe.lis.LisResultNew;
import com.kaos.skynet.api.data.mapper.pipe.lis.LisResultNewMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public class LisResultNewCache extends Cache<LisResultNewCache.Key, List<LisResultNew>> {
    /**
     * 数据库查询实体
     */
    @Autowired
    LisResultNewMapper lisResultNewMapper;

    @Override
    @PostConstruct
    protected void postConstruct() {
        super.postConstruct(Key.class, 300, new Converter<Key, List<LisResultNew>>() {
            @Override
            public List<LisResultNew> convert(Key source) {
                // 检索数据库
                var lisResultNews = lisResultNewMapper.queryInspectResults(new LisResultNewMapper.Key() {
                    {
                        setPatientId(source.patientId);
                        setItemCodes(source.itemCodes);
                        if (source.offset != null) {
                            setBeginDate(LocalDateTime.now().plusDays(-source.offset));
                            setEndDate(LocalDateTime.now());
                        }
                    }
                });
                // 排序 - 时间逆序
                lisResultNews.sort((x, y) -> {
                    return y.getInspectDate().compareTo(x.getInspectDate());
                });
                return lisResultNews;
            }
        });
    }

    /**
     * 缓存键值
     */
    @Data
    public static class Key {
        /**
         * 卡号或住院号
         */
        private String patientId = null;

        /**
         * 检验项目代码列表
         */
        private List<String> itemCodes = null;

        /**
         * 最近几天的数据
         */
        private Integer offset = null;
    }
}
