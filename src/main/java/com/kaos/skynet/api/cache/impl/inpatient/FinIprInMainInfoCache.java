package com.kaos.skynet.api.cache.impl.inpatient;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.mapper.inpatient.FinIprInMainInfoMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 住院流水号 -> 住院信息
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class FinIprInMainInfoCache implements Cache<String, FinIprInMainInfo> {
    /**
     * 数据库接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(FinIprInMainInfoCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, Optional<FinIprInMainInfo>> cache = CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .recordStats()
            .build(new CacheLoader<String, Optional<FinIprInMainInfo>>() {
                @Override
                public Optional<FinIprInMainInfo> load(String key) throws Exception {
                    var ref = FinIprInMainInfoCache.this.inMainInfoMapper.queryInMainInfo(key);
                    return Optional.fromNullable(ref);
                };
            });

    @Override
    public FinIprInMainInfo getValue(String key) {
        try {
            if (key == null) {
                this.logger.warn("键值为空");
                return null;
            } else {
                return this.cache.get(key).orNull();
            }
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
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
