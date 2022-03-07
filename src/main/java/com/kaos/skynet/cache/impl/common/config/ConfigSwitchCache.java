package com.kaos.skynet.cache.impl.common.config;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.skynet.cache.Cache;
import com.kaos.skynet.entity.common.config.ConfigSwitch;
import com.kaos.skynet.mapper.common.config.ConfigSwitchMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 开关编码 -> 开关信息
 * @param 容量 100
 * @param 刷频 1次/1天
 * @param 过期 永不
 */
@Component
public class ConfigSwitchCache implements Cache<String, ConfigSwitch> {
    /**
     * 数据库接口
     */
    @Autowired
    ConfigSwitchMapper switchMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(ConfigSwitchCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, Optional<ConfigSwitch>> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .recordStats()
            .build(new CacheLoader<String, Optional<ConfigSwitch>>() {
                @Override
                public Optional<ConfigSwitch> load(String key) throws Exception {
                    var ref = switchMapper.queryConfigSwitch(key);
                    return Optional.fromNullable(ref);
                };
            });

    @Override
    public ConfigSwitch getValue(String key) {
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
    public View<String, Optional<ConfigSwitch>> show() {
        View<String, Optional<ConfigSwitch>> view = new View<>();
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
