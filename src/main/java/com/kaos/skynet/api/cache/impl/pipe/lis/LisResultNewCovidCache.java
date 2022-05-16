package com.kaos.skynet.api.cache.impl.pipe.lis;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.mapper.pipe.lis.LisResultNewMapper;
import com.kaos.skynet.entity.pipe.lis.LisResultNew;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 就诊卡号/住院号 -> 最近一次LIS核酸检测结果纪录
 * @param 容量 300
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component(value = "covidCache")
public class LisResultNewCovidCache implements Cache<String, LisResultNew> {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(LisResultNewCovidCache.class);

    /**
     * 数据库查询实体
     */
    @Autowired
    LisResultNewMapper lisResultNewMapper;

    /**
     * 缓存实体
     */
    LoadingCache<String, Optional<LisResultNew>> cache = CacheBuilder.newBuilder()
            .maximumSize(300)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .recordStats()
            .build(new CacheLoader<String, Optional<LisResultNew>>() {
                @Override
                public Optional<LisResultNew> load(String key) throws Exception {
                    var rets = LisResultNewCovidCache.this.lisResultNewMapper.queryInspectResult(key,
                            Lists.newArrayList("SARS-CoV-2-RNA"), null, null);
                    if (rets != null && !rets.isEmpty()) {
                        Collections.sort(rets, (x, y) -> {
                            return y.inspectDate.compareTo(x.inspectDate);
                        });
                        return Optional.fromNullable(rets.get(0));
                    }
                    return Optional.absent();
                };
            });

    @Override
    public LisResultNew getValue(String key) {
        try {
            if (key == null) {
                logger.warn("键值为空");
                return null;
            } else {
                return this.cache.get(key).orNull();
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public void refresh(String key) {
        this.cache.refresh(key);
    }

    @Override
    public void refreshAll() {
        for (var key : this.cache.asMap().keySet()) {
            this.refresh(key);
        }
    }

    @Override
    public View show() {
        View view = new View();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = this.cache.asMap();
        return view;
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}
