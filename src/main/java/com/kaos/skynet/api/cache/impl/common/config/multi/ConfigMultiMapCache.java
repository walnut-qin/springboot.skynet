package com.kaos.skynet.api.cache.impl.common.config.multi;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.entity.common.config.ConfigMap;
import com.kaos.skynet.api.mapper.common.config.ConfigMapMapper;
import com.kaos.skynet.core.Gsons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @param 类型 缓存
 * @param 映射 变量名 -> 变量信息
 * @param 容量 100 x 100
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Log4j
@Component
public class ConfigMultiMapCache implements Cache<ConfigMultiMapCache.Key, ConfigMap> {
    /**
     * 数据库接口
     */
    @Autowired
    ConfigMapMapper configMapMapper;

    /**
     * 序列化工具
     */
    Gson gson = Gsons.newGson();

    /**
     * Loading cache
     */
    LoadingCache<String, Optional<ConfigMap>> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .recordStats()
            .build(new CacheLoader<String, Optional<ConfigMap>>() {
                public Optional<ConfigMap> load(String key) throws Exception {
                    // 反序列化key
                    Key realKey = null;
                    try {
                        realKey = gson.fromJson(key, Key.class);
                    } catch (Exception e) {
                        log.error(String.format("反序列化缓存的key失败(%s)", e.getMessage()));
                    }

                    // 查询响应
                    var refData = configMapMapper.queryMultiMapItemValue(realKey.masterKey, realKey.slaveKey);
                    return Optional.fromNullable(refData);
                };
            });

    @Override
    public ConfigMap getValue(Key key) {
        try {
            if (key == null) {
                log.warn("键值为空");
                return null;
            } else {
                return this.cache.get(gson.toJson(key)).orNull();
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public void refresh(Key key) {
        this.cache.refresh(gson.toJson(key));
    }

    @Override
    public void refreshAll() {
        for (var key : this.cache.asMap().keySet()) {
            this.cache.refresh(key);
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

    /**
     * 键
     */
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key {
        /**
         * 主键
         */
        @Setter
        public String masterKey = null;

        /**
         * 从键
         */
        @Setter
        private String slaveKey = null;
    }
}
