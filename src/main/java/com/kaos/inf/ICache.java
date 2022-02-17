package com.kaos.inf;

import java.util.concurrent.ConcurrentMap;

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
    ConcurrentMap<K, V> show();

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
}
