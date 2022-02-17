package com.kaos.his.cache.common.config;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.config.ConfigMap;
import com.kaos.his.mapper.common.config.ConfigMapMapper;
import com.kaos.inf.ICache;

import org.apache.ibatis.executor.ExecutorException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 开关缓存，容量 = 20，不过期，刷新评率 = 1次/天
 */
@Component
public class ConfigMapCache implements ICache<String, ConfigMap> {
    /**
     * 数据库接口
     */
    @Autowired
    ConfigMapMapper configMapMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(ConfigMapCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, ConfigMap> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, ConfigMap>() {
                @Override
                public ConfigMap load(String key) throws Exception {
                    return ConfigMapCache.this.configMapMapper.queryMapValue(key);
                };
            });

    /**
     * 禁止实例化
     * 
     * @throws ExecutorException
     */
    private ConfigMapCache() throws ExecutorException {
    }

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
    public ConcurrentMap<String, ConfigMap> show() {
        return this.cache.asMap();
    }

    @Override
    public void refresh(String key) {
        this.cache.refresh(key);
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}
