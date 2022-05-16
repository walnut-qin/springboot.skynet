package com.kaos.skynet.api.controller.inf.cache;

import com.kaos.skynet.api.cache.Cache.View;

import org.springframework.validation.annotation.Validated;

@Validated
public interface CacheController<K, V> {
    /**
     * 展示cache内容
     */
    View show();

    /**
     * 清空cache
     */
    String clear();
}
