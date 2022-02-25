package com.kaos.his.controller.inf.cache;

import com.kaos.inf.ICache.View;

public interface ICacheController<K, V> {
    /**
     * 展示cache内容
     */
    View<K, ?> show();

    /**
     * 刷新cache的值
     */
    String refresh(K key);

    /**
     * 刷新全部cache的值
     */
    String refreshAll();

    /**
     * 清空cache
     */
    String clear();
}
