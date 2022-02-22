package com.kaos.inf;

import java.util.concurrent.ConcurrentMap;

import com.google.common.cache.CacheStats;

public interface ICache<K, V> {
    /**
     * 获取值
     * 
     * @param key
     * @return
     */
    V getValue(K key);

    /**
     * 展示cache内容
     * 
     * @return
     */
    View<K, V> show();

    /**
     * 显式刷新某一个元素
     * 
     * @param key
     */
    void refresh(K key);

    /**
     * 清除缓存
     * 
     * @param key
     */
    void invalidateAll();

    /**
     * 缓存视图
     */
    public class View<K, V> {
        /**
         * 当前容量
         */
        public Long size = null;

        /**
         * 统计数据
         */
        public CacheStats stats = null;

        /**
         * 缓存内容
         */
        public ConcurrentMap<K, V> cache = null;
    }
}
