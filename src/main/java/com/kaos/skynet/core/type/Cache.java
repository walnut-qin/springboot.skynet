package com.kaos.skynet.core.type;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.kaos.skynet.core.Gsons;

import org.springframework.core.convert.converter.Converter;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
public abstract class Cache<K extends Object, V extends Object> {
    /**
     * 序列化工具
     */
    Gson gson = null;

    /**
     * 缓存实体
     */
    LoadingCache<String, Optional<V>> loadingCache = null;

    /**
     * 完全构造函数
     * 
     * @param gson
     * @param size
     * @param cacheLoader
     */
    public Cache(Class<K> classOfK, Integer size, Gson gson, Converter<K, V> converter) {
        // 记录序列化工具
        this.gson = gson;

        // 构造缓存实体
        this.loadingCache = CacheBuilder.newBuilder()
                .maximumSize(size)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .recordStats()
                .build(new CacheLoader<String, Optional<V>>() {
                    @Override
                    public Optional<V> load(String key) throws Exception {
                        // 反序列化出实际的key
                        K realKey = gson.fromJson(key, classOfK);

                        // 使用传入的loader加载对象
                        return Optional.fromNullable(converter.convert(realKey));
                    }
                });
    }

    /**
     * 缺省构造函数
     * 
     * @param gson
     * @param size
     * @param cacheLoader
     */
    public Cache(Class<K> classOfK, Integer size, Converter<K, V> converter) {
        this(classOfK, size, Gsons.newGson(), converter);
    }

    /**
     * 从缓存中获取值
     * 
     * @param key
     * @return
     */
    public V get(K key) {
        // 序列化出真实的Key
        String keyStr = gson.toJson(key);
        if (keyStr == null) {
            log.warn("缓存不支持null键");
            return null;
        }

        // 检索缓存
        try {
            return loadingCache.get(keyStr).orNull();
        } catch (Exception e) {
            log.warn(String.format("检索缓存出现异常(%s)", e.getMessage()));
            return null;
        }
    }

    /**
     * 展示缓存内容
     * 
     * @return
     */
    public State show() {
        // 构造实体
        State state = new State();
        state.size = loadingCache.size();
        state.stats = loadingCache.stats();
        ConcurrentMap<String, V> cache = Maps.newConcurrentMap();
        for (var key : loadingCache.asMap().keySet()) {
            cache.put(key, loadingCache.getIfPresent(key).get());
        }
        state.cache = cache;
        return state;
    }

    /**
     * 缓存状态实体
     */
    public class State {
        /**
         * 当前容量
         */
        @Getter
        private Long size = null;

        /**
         * 统计数据
         */
        @Getter
        private CacheStats stats = null;

        /**
         * 缓存内容
         */
        @Getter
        private ConcurrentMap<String, V> cache = null;
    }
}
