package com.kaos.his.cache.impl.common.config;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.kaos.his.cache.Cache;
import com.kaos.his.entity.common.config.ConfigMap;
import com.kaos.his.mapper.common.config.ConfigMapMapper;

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
    LoadingCache<String, Cache<String, ConfigMap>> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .recordStats()
            .build(new CacheLoader<String, Cache<String, ConfigMap>>() {
                @Override
                public Cache<String, ConfigMap> load(String key1) throws Exception {
                    return new Cache<String, ConfigMap>() {
                        Logger logger = Logger.getLogger(this.getClass());

                        LoadingCache<String, ConfigMap> cache = CacheBuilder.newBuilder()
                                .maximumSize(20)
                                .refreshAfterWrite(1, TimeUnit.DAYS)
                                .build(new CacheLoader<String, ConfigMap>() {
                                    @Override
                                    public ConfigMap load(String key2) throws Exception {
                                        return ConfigMultiMapCache.this.configMapMapper.queryMultiMapItemValue(key1,
                                                key2);
                                    }
                                });

                        @Override
                        public ConfigMap getValue(String key) {
                            try {
                                if (key == null) {
                                    this.logger.warn("键值为空");
                                    return null;
                                } else {
                                    return this.cache.get(key);
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
                        public View<String, ConfigMap> show() {
                            View<String, ConfigMap> view = new View<>();
                            view.size = this.cache.size();
                            view.stats = this.cache.stats();
                            view.cache = this.cache.asMap();
                            return view;
                        }

                        @Override
                        public void invalidateAll() {
                            this.cache.invalidateAll();

                        }
                    };
                };
            });

    @Override
    public Cache<String, ConfigMap> getValue(String key) {
        try {
            if (key == null) {
                this.logger.warn("键值为空");
                return null;
            } else {
                return this.cache.get(key);
            }
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public void refresh(String key) {
        try {
            this.cache.get(key).refreshAll();
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
    public View<String, View<String, ?>> show() {
        View<String, View<String, ?>> view = new View<>();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = Maps.newConcurrentMap();
        for (var key : this.cache.asMap().keySet()) {
            try {
                view.cache.put(key, this.cache.get(key).show());
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
