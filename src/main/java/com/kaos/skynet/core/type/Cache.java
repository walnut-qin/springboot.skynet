package com.kaos.skynet.core.type;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.kaos.skynet.core.Gsons;

import org.springframework.core.convert.converter.Converter;

import lombok.extern.log4j.Log4j;

@Log4j
public abstract class Cache<K extends Object, V extends Object> {
    /**
     * 序列化工具
     */
    final Gson gson = Gsons.newGson();

    /**
     * 缓存实体
     */
    LoadingCache<String, Optional<V>> loadingCache;

    /**
     * 必须调用后构造函数构造成员变量
     */
    protected abstract void postConstruct();

    /**
     * 后初始化
     * 
     * @param size
     * @param converter
     */
    @SuppressWarnings("unchecked")
    protected void postConstruct(Integer size, Converter<K, V> converter) {
        // 构造缓存实体
        this.loadingCache = CacheBuilder.newBuilder()
                .maximumSize(size)
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .refreshAfterWrite(15, TimeUnit.SECONDS)
                .recordStats()
                .build(new CacheLoader<String, Optional<V>>() {
                    @Override
                    public Optional<V> load(String key) throws Exception {
                        // 反射出classOfK
                        Class<K> classOfK = null;
                        var type = getClass().getGenericSuperclass();
                        if (type instanceof ParameterizedType) {
                            classOfK = (Class<K>) ((ParameterizedType) type).getActualTypeArguments()[0];
                        }

                        // 反序列化出实际的key
                        K realKey = gson.fromJson(key, classOfK);

                        // 使用传入的loader加载对象
                        return Optional.fromNullable(converter.convert(realKey));
                    }
                });
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
     * 获取克隆体
     */
    @SuppressWarnings("unchecked")
    public V getClone(K key) {
        // 获取原始数据
        var val = get(key);
        if (val == null) {
            return null;
        } else {
            // 序列化
            String str = gson.toJson(val);

            // 反射出classOfV
            Class<V> classOfV = null;
            var type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                classOfV = (Class<V>) ((ParameterizedType) type).getActualTypeArguments()[1];
            }

            // 反序列化
            return gson.fromJson(str, classOfV);
        }
    }

    /**
     * 刷新缓存的值
     * 
     * @param key
     */
    public void refresh(K key) {
        // 序列化出真实的Key
        String keyStr = gson.toJson(key);
        if (keyStr == null) {
            log.warn("缓存不支持null键");
        }

        // 检索缓存
        loadingCache.refresh(keyStr);
    }

    /**
     * 展示缓存内容
     * 
     * @return
     */
    public CacheStats getStats() {
        return loadingCache.stats();
    }
}
