package com.kaos.skynet.core.type.thread.lock;

public interface Lock<K> {
    /**
     * 获取锁实体
     * 
     * @param key
     * @return
     */
    Object get(K key);

    /**
     * 获取锁总量
     * 
     * @return
     */
    Integer size();

    /**
     * 获取锁名
     * 
     * @return
     */
    String name();
}
