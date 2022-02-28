package com.kaos.his.controller.inf.cache;

import javax.validation.constraints.NotNull;

import com.kaos.his.cache.Cache.View;

import org.springframework.validation.annotation.Validated;

@Validated
public interface CacheController<K, V> {
    /**
     * 展示cache内容
     */
    View<K, ?> show();

    /**
     * 刷新cache的值
     */
    String refresh(@NotNull(message = "键值不能为空") K key);

    /**
     * 刷新全部cache的值
     */
    String refreshAll();

    /**
     * 清空cache
     */
    String clear();
}
