package com.kaos.skynet.api.cache.impl.common.undrug;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.mapper.common.undrug.FinComUndrugInfoMapper;
import com.kaos.skynet.entity.common.undrug.FinComUndrugInfo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 非药品项目编码 -> 项目信息
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 1min
 */
@Component
public class FinComUndrugInfoCache implements Cache<String, FinComUndrugInfo> {
    /**
     * 数据库接口
     */
    @Autowired
    FinComUndrugInfoMapper undrugInfoMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(FinComUndrugInfoCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, Optional<FinComUndrugInfo>> cache = CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .recordStats()
            .build(new CacheLoader<String, Optional<FinComUndrugInfo>>() {
                @Override
                public Optional<FinComUndrugInfo> load(String key) throws Exception {
                    var ref = FinComUndrugInfoCache.this.undrugInfoMapper.queryUndrugInfo(key);
                    return Optional.fromNullable(ref);
                };
            });

    @Override
    public FinComUndrugInfo getValue(String key) {
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
    public View<String, Optional<FinComUndrugInfo>> show() {
        View<String, Optional<FinComUndrugInfo>> view = new View<>();
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
