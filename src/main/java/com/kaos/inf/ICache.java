package com.kaos.inf;

import java.util.concurrent.ConcurrentMap;

public interface ICache<E> {
    /**
     * 获取值
     * 
     * @param key
     * @return
     */
    E getValue(String key);

    /**
     * 展示cache内容
     * 
     * @return
     */
    ConcurrentMap<String, E> show();

    /**
     * 显式刷新某一个元素
     * 
     * @param key
     */
    void refresh(String key);

    /**
     * 清除缓存
     * 
     * @param key
     */
    void invalidateAll();
}
