package com.kaos.skynet.api.cache.impl.common.config.multi;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.mapper.common.config.ConfigMapMapper;
import com.kaos.skynet.entity.common.config.ConfigMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 变量名 -> 变量信息
 * @param 容量 100
 * @param 刷频 1次/1天
 * @param 过期 永不
 */
@Component
public class ConfigMultiMapCache implements Cache<String, Cache<String, ConfigMap>> {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(ConfigMultiMapCache.class);

    /**
     * 数据库接口
     */
    @Autowired
    ConfigMapMapper configMapMapper;

    /**
     * Loading cache
     */
    LoadingCache<String, Optional<Cache<String, ConfigMap>>> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .recordStats()
            .build(new CacheLoader<String, Optional<Cache<String, ConfigMap>>>() {
                public Optional<Cache<String, ConfigMap>> load(String key1)
                        throws Exception {
                    return Optional.fromNullable(new Cache<String, ConfigMap>() {
                        /**
                         * 日志接口
                         */
                        Logger logger = Logger.getLogger(this.getClass());

                        LoadingCache<String, Optional<ConfigMap>> cache = CacheBuilder.newBuilder()
                                .maximumSize(100)
                                .refreshAfterWrite(1, TimeUnit.DAYS)
                                .recordStats()
                                .build(new CacheLoader<String, Optional<ConfigMap>>() {
                                    public Optional<ConfigMap> load(String key2) throws Exception {
                                        var ref = configMapMapper.queryMultiMapItemValue(key1, key2);
                                        return Optional.fromNullable(ref);
                                    };
                                });

                        @Override
                        public ConfigMap getValue(String key) {
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
                        public View<String, ?> show() {
                            View<String, Optional<ConfigMap>> view = new View<>();
                            view.size = this.cache.size();
                            view.stats = this.cache.stats();
                            view.cache = this.cache.asMap();
                            return view;
                        }

                        @Override
                        public void invalidateAll() {
                            this.cache.invalidateAll();
                        }
                    });
                };
            });

    @Override
    public Cache<String, ConfigMap> getValue(String key) {
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
        try {
            var subCache = this.cache.get(key).orNull();
            if (subCache != null) {
                subCache.refreshAll();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void refreshAll() {
        for (var key : this.cache.asMap().keySet()) {
            this.refresh(key);
        }
    }

    @Override
    public View<String, ?> show() {
        View<String, View<String, ?>> view = new View<>();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = Maps.newConcurrentMap();
        for (var key : this.cache.asMap().keySet()) {
            try {
                var subCache = this.cache.get(key).orNull();
                if (subCache != null) {
                    view.cache.put(key, subCache.show());
                }
            } catch (Exception e) {
                this.logger.warn(e.getMessage());
                continue;
            }
        }
        return view;
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}
